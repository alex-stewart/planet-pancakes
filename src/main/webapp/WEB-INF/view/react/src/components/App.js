import React, {Component} from 'react';
import {BrowserRouter, Route} from 'react-router-dom';
import WorldMap from './WorldMap';
import Navigation from './Navigation';
import Wiki from './Wiki';

export default class App extends Component {

    render() {
        return ([
            <BrowserRouter key="browser-router">
                <div className="main-container">
                    <Navigation/>
                    <Route exact path="/"/>
                    <Route path="/map" component={WorldMap}/>
                    <Route path="/wiki" component={Wiki}/>
                </div>
            </BrowserRouter>
        ]);
    }
}
