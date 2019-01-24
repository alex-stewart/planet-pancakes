import React, {Component} from 'react';
import WorldMap from './WorldMap';
import Navigation from './Navigation';

export default class App extends Component {
    render() {
        return ([
            <Navigation key={"navigation-bar"}/>,
            <WorldMap key={"world-map"}/>
        ]);
    }
}
