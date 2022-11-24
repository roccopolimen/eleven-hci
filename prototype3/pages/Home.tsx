import { View } from "react-native";
import { useNavigation } from '@react-navigation/native';
import { Card, Surface, Text } from "react-native-paper";
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../App';

type HomeScreenProp = StackNavigationProp<RootStackParamList, 'Home'>;

const Home = () => {
    const { navigate } = useNavigation<HomeScreenProp>();

    const handlePlay = () => navigate("Game");
    const handleLeaderboard = () => navigate("Leaderboard");
    const handleRules = () => navigate("Rules");
    return (<>
        <View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
            <Text style={{ fontSize: 40 }}>Eleven</Text>
        </View>
        <View style={{ flex: 1, justifyContent: "center" }}>
        <Surface style={{ padding: 20 }}>
        <Card elevation={3} style={{ backgroundColor: "#e0e0e0" }} onPress={handlePlay}>
            <Card.Title title="Play" titleStyle={{ textAlign: "center" }} />
        </Card>
        </Surface>
        <Surface style={{ padding: 20 }}>
        <Card style={{ backgroundColor: "#e0e0e0" }} onPress={handleLeaderboard}>
            <Card.Title title="Leaderboard" titleStyle={{ textAlign: "center" }} />
        </Card>
        </Surface>
        <Surface style={{ padding: 20 }}>
        <Card style={{ backgroundColor: "#e0e0e0" }} onPress={handleRules}>
            <Card.Title title="How to Play" titleStyle={{ textAlign: "center" }} />
        </Card>
        </Surface>
        </View>
    </>);
};

export default Home;