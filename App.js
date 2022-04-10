/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import type {Node} from 'react';
import {
  Text,
  View,
} from 'react-native';


const App: () => Node = () => {
  return (
    <View
      style={{
        flex: 1,
        justifyContent: 'center',
        alignItems: "center"
      }}>
    <Text>Welcome to reBalance alpha</Text>
    <Text style="font-size: 0.1em;">(This app does not have a view currently)</Text>
  </View>
  );
};


export default App;
