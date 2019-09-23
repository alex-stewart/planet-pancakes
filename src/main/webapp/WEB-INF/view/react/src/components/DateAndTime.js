import React, {Component} from 'react';

const MILLISECONDS_IN_DAY = 86400000;
const CYCLES_IN_YEAR = 8;
const DAYS_IN_CYCLE = 14;
const CYCLE_DAY_NAMES = [
    "Wonday", "Tooday", "Triday", "Forday", "Thiffday", "Ixday", "Kingsday",
    "Newday", "Nonday", "Shuhday", "Sheday", "Sharday", "Queensday", "Emperorday"
];

export default class DateAndTime extends Component {

    constructor(props) {
        super(props);
        this.state = {
            showTime: props.time,
            date: new Date()
        };
    };

    componentDidMount() {
        this.timerID = setInterval(
            () => this.tick(),
            1000
        );
    }

    componentWillUnmount() {
        clearInterval(this.timerID);
    }

    tick() {
        this.setState({
            date: new Date(),
        });
    }

    formatDate() {
        if (this.state.showTime) {
            return this.state.date.toLocaleTimeString('en-GB', {hour12: false});
        } else {
            return "";
        }
    }

    render() {
        let days = Math.ceil(this.state.date / MILLISECONDS_IN_DAY);
        let year = Math.ceil(days / (CYCLES_IN_YEAR * DAYS_IN_CYCLE));
        let dayOfYear = Math.ceil(days % (CYCLES_IN_YEAR * DAYS_IN_CYCLE));
        let cycleOfYear = Math.ceil(dayOfYear / DAYS_IN_CYCLE);
        let dayOfCycle = Math.floor(dayOfYear % DAYS_IN_CYCLE);

        return <div>
            {year}y {cycleOfYear}c {CYCLE_DAY_NAMES[dayOfCycle]} {this.formatDate()}
        </div>

    }
}