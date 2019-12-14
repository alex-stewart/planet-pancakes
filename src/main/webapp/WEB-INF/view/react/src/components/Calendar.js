import React, {Component} from 'react';
import {GameDate} from '../util/GameDate';

export default class Calendar extends Component {

    constructor(props) {
        super(props);
        this.state = {
            gameDate: GameDate.now()
        };
    }

    render() {
        return (
            <div className={"pp-page"}>
                {this.state.gameDate.toString()}
            </div>
        )
    }
}