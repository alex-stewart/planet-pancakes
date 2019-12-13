import React, {Component} from 'react';
import {formatDate, formatDateAndTime} from '../util/date-utils';

const TICK_TIMEOUT = 1000;

export default class DateAndTime extends Component {

    constructor(props) {
        super(props);
        this.state = {
            time: props.date || new Date()
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
            time: new Date(),
        });
    }

    formatTime () {
        if (this.props.time) {
            return formatDateAndTime(this.state.time)
        } else {
            return formatDate(this.state.time)
        }
    };


    render() {
        return <div>
            {
                this.formatTime()
            }
        </div>

    }
}