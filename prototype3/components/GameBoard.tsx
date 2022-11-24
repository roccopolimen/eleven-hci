import { useEffect, useState } from "react";
import { Text, View } from "react-native";
import { Button, FAB, Modal, Portal } from "react-native-paper";
import { GestureHandlerRootView } from 'react-native-gesture-handler';
import { DraxProvider, DraxView } from 'react-native-drax';
import ScoreBoard from "../components/ScoreBoard";
import AsyncStorage from '@react-native-async-storage/async-storage';

export enum COLOR {
    RED,
    YELLOW,
    GREEN,
    BLUE,
    GRAY,
    WHITE
};
type Tile = {
    value: number;
    color: COLOR;
};
const colorMap = {
    [COLOR.RED]: "red",
    [COLOR.YELLOW]: "yellow",
    [COLOR.GREEN]: "green",
    [COLOR.BLUE]: "blue",
    [COLOR.GRAY]: "gray",
    [COLOR.WHITE]: "white"
};

const GameBoard = () => {
    const [highestScore, setHighestScore] = useState<number | undefined>(undefined);
    const [currentScore, setCurrentScore] = useState<number>(0);
    const [board, setBoard] = useState<Array<Array<Tile>>>(undefined);
    const [tray, setTray] = useState<Array<Tile>>(undefined);
    const [draggable, setDraggable] = useState<Array<boolean>>([]);
    const [isPlaying, setIsPlaying] = useState<boolean>(false);
    const [intervalId, setIntervalId] = useState<NodeJS.Timer | undefined>(undefined);
    const [gameWon, setGameWon] = useState<boolean>(false);
    const [gameLost, setGameLost] = useState<boolean>(false);

    useEffect(() => {
        handleResetGame();
        (async () => {
            const scores = JSON.parse(await AsyncStorage.getItem("scores"));
            setHighestScore(scores[0]);
        })();
    }, []);

    // when the tray changes, a tile has been placed on the board
    // check for win and loss conditions
    useEffect(() => {
        if(!isPlaying) return;

        if (board.every(row => row.every(tile => tile.value !== 0))) {
            handleWin();
        } else if (tray.slice(0, 3).every(tile => {
            return board.every((row, rowIndex) => row.every((_, columnIndex) => {
                return !canTileBePlaced(tile, rowIndex, columnIndex);
            }));
        })) {
            handleLoss();
        }
        const newDraggable = tray.slice(0, 3).map(tile => {
            return board.every((row, rowIndex) => row.every((_, columnIndex) => {
                return !canTileBePlaced(tile, rowIndex, columnIndex);
            }));
        });
        setDraggable(newDraggable.map(bool => !bool));
    }, [tray]);

    const canTileBePlaced = (tile: Tile, i: number, j: number) => {
        if(board[i][j].color === COLOR.GRAY) {
            if(i > 0 && board[i - 1][j].color === tile.color && board[i - 1][j].value > tile.value) return true;
            if(i < 4 && board[i + 1][j].color === tile.color && board[i + 1][j].value > tile.value) return true;
            if(j > 0 && board[i][j - 1].color === tile.color && board[i][j - 1].value > tile.value) return true;
            if(j < 4 && board[i][j + 1].color === tile.color && board[i][j + 1].value > tile.value) return true;
        }
        return false;
    };

    const handleWin = () => {
        setIsPlaying(false);
        clearInterval(intervalId);
        setGameWon(true);
        if (highestScore === undefined || currentScore > highestScore) {
            setHighestScore(currentScore);
        }
        (async () => {
            const scores = JSON.parse(await AsyncStorage.getItem("scores"));
            scores.push(currentScore);
            // sort scores

            scores.sort((a, b) => b - a);
            await AsyncStorage.setItem("scores", JSON.stringify(scores));
        })();
    };

    const handleLoss = () => {
        setIsPlaying(false);
        clearInterval(intervalId);
        setGameLost(true);
    };

    const handleResetGame = () => {
        // stop the interval
        if(intervalId !== undefined) clearInterval(intervalId);
        setIntervalId(undefined);
        setIsPlaying(false);
        setCurrentScore(0);
        setGameWon(false);
        setGameLost(false);
        setDraggable([true, true, true]);

        // reset board
        const newBoard = [];
        for(let i = 0; i < 5; i++) {
            const row = [];
            for(let j = 0; j < 5; j++) {
                row.push({
                    value: 0,
                    color: COLOR.GRAY
                });
            }
            newBoard.push(row);
        }
        newBoard[0][0] = { value: 11, color: COLOR.RED };
        newBoard[0][4] = { value: 11, color: COLOR.YELLOW };
        newBoard[4][0] = { value: 11, color: COLOR.GREEN };
        newBoard[4][4] = { value: 11, color: COLOR.BLUE };
        setBoard(newBoard);
        
        // reset tray
        const newTray = [];
        for (let i = 1; i <= 10; i++) {
            newTray.push({ value: i, color: COLOR.RED });
            newTray.push({ value: i, color: COLOR.YELLOW });
            newTray.push({ value: i, color: COLOR.GREEN });
            newTray.push({ value: i, color: COLOR.BLUE });
        }
        for (let i = newTray.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * i);
            const temp = newTray[i];
            newTray[i] = newTray[j];
            newTray[j] = temp;
        }
        setTray(newTray);
    };

    const showAvailableMoves = (tile_index: number) => {
        const tile: Tile = tray[tile_index];
        const newBoard = [...board];
        for(let i = 0; i < 5; i++) {
            for(let j = 0; j < 5; j++) {
                if(newBoard[i][j].color === COLOR.GRAY) {
                    if(i > 0 && newBoard[i - 1][j].color === tile.color && newBoard[i - 1][j].value > tile.value) {
                        newBoard[i][j].color = COLOR.WHITE;
                    }
                    if(i < 4 && newBoard[i + 1][j].color === tile.color && newBoard[i + 1][j].value > tile.value) {
                        newBoard[i][j].color = COLOR.WHITE;
                    }
                    if(j > 0 && newBoard[i][j - 1].color === tile.color && newBoard[i][j - 1].value > tile.value) {
                        newBoard[i][j].color = COLOR.WHITE;
                    }
                    if(j < 4 && newBoard[i][j + 1].color === tile.color && newBoard[i][j + 1].value > tile.value) {
                        newBoard[i][j].color = COLOR.WHITE;
                    }
                }
            }
        }
        setBoard(newBoard);
    };


    if(board === undefined) return (<></>);
    return (<>
        <ScoreBoard time={currentScore} bestTime={highestScore} />
        <GestureHandlerRootView style={{ flex: 1 }}>
        <DraxProvider>
        <View style={{ paddingTop: "10%", alignItems: "center" }}>
        {board.map((row, i) =>
            <View key={`row ${i}`} style={{ flexDirection: "row", margin: 1, height: "15%" }}>
                {row.map((tile, j) =>
                <DraxView
                    style={[styles.centeredContent, styles.receivingZone, { backgroundColor: colorMap[tile.color] }]}
                    receivingStyle={styles.receiving}
                    renderContent={() => <Text style={{ fontSize: 20 }}>{!tile.value ? " " : tile.value}</Text>}
                    key={`index ${i} ${j}`}
                    onReceiveDragDrop={(event) => {
                        if(board[i][j].color === COLOR.WHITE) {
                            let tray_index = event.dragged.payload;
                            let placed_tile = tray[tray_index];
                            let new_board = [...board];
                            new_board[i][j] = placed_tile;
                            setBoard(new_board);
                            let new_tray = [...tray];
                            new_tray.splice(tray_index, 1);
                            setTray(new_tray);
                        }
                        setBoard(board.map((row) => row.map((tile) => {
                            if(tile.color === COLOR.WHITE) {
                                return { value: 0, color: COLOR.GRAY };
                            }
                            return tile;
                        })));
                        if(!isPlaying) {
                            const timestamp = (new Date()).getTime();
                            setIsPlaying(true);
                            setIntervalId(setInterval(() => {
                                setCurrentScore(((new Date()).getTime() - timestamp) / 1000.0);
                            }, 200));
                        }
                    }}
                />
                )}
            </View>
        )}
        </View>
        <View style={{ flexDirection: "row", justifyContent: "center", height: "10%" }}>
            {tray.slice(0, 3).map((tile, i) =>
                <DraxView
                    draggable={draggable[i]}
                    style={[styles.centeredContent, styles.draggableBox, { backgroundColor: colorMap[tile.color], opacity: draggable[i] ? 1 : 0.5 }]}
                    draggingStyle={styles.dragging}
                    dragReleasedStyle={styles.dragging}
                    hoverDraggingStyle={styles.hoverDragging}
                    dragPayload={i}
                    longPressDelay={80}
                    key={i}
                    onDragStart={() => showAvailableMoves(i)}
                    onDragEnd={() => setBoard(board.map(row => row.map(tile => tile.color === COLOR.WHITE ? { ...tile, color: COLOR.GRAY } : tile)))}
                >
                <View key={`tray ${i}`} style={
                    {
                        
                    }
                }>
                        <Text style={{ fontSize: 20, margin: 0 }}>{!tile.value ? " " : tile.value}</Text>
                </View>
                </DraxView>
            )}
        </View>
        <FAB
            style={{ position: "absolute", margin: 16, right: 0, bottom: 0, backgroundColor: "black" }}
            small
            icon="refresh"
            color="red"
            onPress={handleResetGame}
        />
        </DraxProvider>
        </GestureHandlerRootView>
        <Portal>
        <Modal visible={gameLost} style={{ alignItems: "center", flex: 1 }}>
            <View style={{ height: "60%", backgroundColor: "white",  justifyContent: "center", alignItems: "center", paddingHorizontal: "20%" }}>
                <Text>Sorry! Game Over</Text>
                <Button onPress={handleResetGame}>Play Again</Button>
            </View>
        </Modal>
        </Portal>
        <Portal>
        <Modal visible={gameWon} style={{ alignItems: "center", flex: 1 }}>
            <View style={{ height: "60%", backgroundColor: "white",  justifyContent: "center", alignItems: "center", paddingHorizontal: "20%" }}>
                <Text>Congratulations! You Won</Text>
                <Button onPress={handleResetGame}>Play Again</Button>
            </View>
        </Modal>
        </Portal>
    </>);
};

const styles = {
    container: {
      flex: 1,
      padding: 12,
      paddingTop: 40,
      justifyContent: 'space-evenly',
    },
    centeredContent: {
      borderRadius: 10,
    },
    receivingZone: {
        justifyContent: 'center',
        alignItems: 'center',
        width: "19%",
        height: "100%",
        margin: 1,
        borderRadius: 10,
        marginRight: 5
    },
    receiving: {
      borderColor: 'red',
      borderWidth: 2,
    },
    draggableBox: {
        justifyContent: 'center',
        alignItems: 'center',
        width: "19%",
        height: "100%",
        margin: 1,
        borderRadius: 10,
        marginRight: 5
    },
    dragging: {
      opacity: 0.2,
    },
    hoverDragging: {
      borderColor: 'magenta',
      borderWidth: 2,
    },
    receivingContainer: {
      flexDirection: 'row',
      justifyContent: 'space-evenly'
    },
    itemSeparator: {
      height: 15
    },
    draxListContainer: {
      padding: 5,
      height: 200
    },
    receivingZoneContainer: {
      padding: 5,
      height: 100
    },
    textStyle: {
      fontSize: 18
    },
    headerStyle: {
      marginTop: 20,
      fontSize: 18,
      fontWeight: 'bold',
      marginLeft: 20
    }
};

export default GameBoard;