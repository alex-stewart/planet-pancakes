import React, {Component} from 'react';
import {BrowserRouter, Route} from 'react-router-dom';
import WorldMap from './WorldMap';
import Navigation from './Navigation';
import User from './User';
import News from './News'
import axios from "axios";

export default class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            currentUser: null,
            authenticated: false,
            loading: false
        };
    }

    loadCurrentlyLoggedInUser() {
        this.setState({
            loading: true
        });

        axios.get("/api/users/me")
            .then(
                (result) => {
                    this.setState({
                        currentUser: result.data,
                        authenticated: true,
                        loading: false
                    });
                },
                (error) => {
                    this.setState({
                        loading: false
                    });
                }
            )
    }

    handleLogout() {
        this.setState({
            authenticated: false,
            currentUser: null
        });
    }

    componentDidMount() {
        this.loadCurrentlyLoggedInUser();
    }

    render() {
        return ([
            <BrowserRouter key="browser-router">
                <div className="main-container">
                    <Navigation user={this.state.currentUser}/>
                    <Route exact path="/" component={WorldMap}/>
                    <Route path="/map" component={WorldMap}/>
                    <Route path="/user"
                           render={(props) => <User {...props} user={this.state.currentUser}/>}
                    />
                    <Route path="/news" component={News}/>
                </div>
            </BrowserRouter>
        ]);
    }
}
