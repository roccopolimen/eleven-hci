import 'react-native-gesture-handler';
import { useEffect, useState } from 'react';
import { Provider as PaperProvider } from 'react-native-paper';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import Home from './pages/Home';
import Game from './pages/Game';
import Leaderboard from './pages/Leaderboard';
import AsyncStorage from '@react-native-async-storage/async-storage';
import Rules from './pages/Rules';

export type RootStackParamList = {
  Home: undefined;
  Game: undefined;
  Leaderboard: undefined;
  Rules: undefined;
};
const Stack = createStackNavigator<RootStackParamList>();

const App = () => {
  const [initializingScores, setInitializingScores] = useState(true);

  useEffect(() => {
    (async () => {
      await AsyncStorage.setItem('scores', JSON.stringify([]));
      setInitializingScores(false);
    })();
  }, []);


  if(initializingScores) return null;
  return (
    <PaperProvider>
    <NavigationContainer>
    <Stack.Navigator initialRouteName="Home" screenOptions={{ headerShown: false }}>
      <Stack.Screen name="Home" component={Home} />
      <Stack.Screen name="Game" component={Game} />
      <Stack.Screen name="Leaderboard" component={Leaderboard} />
      <Stack.Screen name="Rules" component={Rules} />
    </Stack.Navigator>
    </NavigationContainer>
    </PaperProvider>
  );
};

export default App;