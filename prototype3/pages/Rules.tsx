import { View } from "react-native";
import { Text } from "react-native-paper";

const Rules = () => {
    return (<>
        <View style={{ paddingTop: "40%", alignItems: "center" }}>
            <Text style={{ fontSize: 40, paddingBottom: "10%" }}>How To Play</Text>
        </View>
        <View style={{ paddingLeft: "10%", paddingRight: "10%" }}>
            <Text style={{ fontSize: 20, paddingBottom: "10%" }}>{`\u2022 The objective of the game is to fill the board completely with tiles as quickly as possible!`}</Text>
            <Text style={{ fontSize: 20, paddingBottom: "10%" }}>{`\u2022 In order to place a tile onto the board, it must be adjacent to a tile with the same color as it and larger numerical value.`}</Text>
            <Text style={{ fontSize: 20, paddingBottom: "10%" }}>{`\u2022 Drag tiles from the tray at the bottom onto available cells in the grid.`}</Text>
        </View>
    </>);
};

export default Rules;