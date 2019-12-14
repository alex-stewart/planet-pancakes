import React, {Component} from 'react';
import {GameDate} from '../util/GameDate';

const TICK_TIMEOUT = 1000;

export default class DateAndTime extends Component {

    constructor(props) {
        super(props);
        this.state = {
            gameDate: GameDate.now()
        };
    };

    componentDidMount() {
        this.timerID = setInterval(
            () => this.tick(),
            TICK_TIMEOUT
        );
    }

    componentWillUnmount() {
        clearInterval(this.timerID);
    }

    tick() {
        this.setState({
            gameDate: GameDate.now(),
        });
    }

    render() {
        let date = "";
        if (this.state.gameDate) {
            date = this.state.gameDate.toString()
        }

        return (
            <div>
                <div className={"navbar-date-time"}>
                    {date}
                </div>
            </div>
        )
    }
}