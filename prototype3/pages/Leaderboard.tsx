import { useEffect, useState } from "react";
import { View } from "react-native";
import { Text } from "react-native-paper";
import AsyncStorage from '@react-native-async-storage/async-storage';
const Leaderboard = () => {
    
    const [scores, setScores] = useState<Array<number>>([]);

    useEffect(() => {
        (async () => {
            setScores(JSON.parse(await AsyncStorage.getItem("scores")));
        })();
    }, []);
    return (<>
        <View style={{ flex: 1, alignItems: "center", paddingTop: "20%" }}>
            <Text style={{ fontSize: 40, paddingBottom: "10%" }}>Leaderboard</Text>
            {!scores.length && <Text style={{ fontSize: 20, paddingBottom: "10%" }}>No scores yet!</Text>}
            {scores.map((score, index) => <Text key={index} style={{ fontSize: 20 }}>{score}</Text>)}
        </View>
    </>);
};

export default Leaderboard;