import { View } from "react-native";
import { Card, Surface, Text } from "react-native-paper";

interface PropType {
    time: number;
    bestTime: number;
};
const ScoreBoard = (props: PropType) => {
    const { time:currentTime, bestTime } = props;
    return (<>
        {/** make view fill entire width with items centered */}
        <View style={{ flexDirection: "row", paddingTop: "10%" }}>
            <Card style={{ width: "50%" }}>
                <Card.Title title="Current Time" />
                <Card.Content>
                    <Text>{currentTime}</Text>
                </Card.Content>
            </Card>
            <Card style={{ width: "50%" }}>
                <Card.Title title="Best Time" />
                <Card.Content>
                    <Text>{bestTime === undefined ? "---" : bestTime}</Text>
                </Card.Content>
            </Card>
        </View>
    </>);
};

export default ScoreBoard;