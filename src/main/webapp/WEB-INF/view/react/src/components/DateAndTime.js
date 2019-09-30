import React, {Component} from 'react';

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
            time: new Date()
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
            time: new Date(),
        });
    }

    formatTime() {
        if (this.state.showTime) {
            return this.state.time.toLocaleTimeString('en-GB', {hour12: false});
        } else {
            return "";
        }
    }


    render() {
        let year = Math.ceil(this.props.days / (CYCLES_IN_YEAR * DAYS_IN_CYCLE));
        let dayOfYear = Math.ceil(this.props.days % (CYCLES_IN_YEAR * DAYS_IN_CYCLE));
        let cycleOfYear = Math.ceil(dayOfYear / DAYS_IN_CYCLE);
        let dayOfCycle = Math.floor(dayOfYear % DAYS_IN_CYCLE);

        return <div>
            {year}y {cycleOfYear}c {CYCLE_DAY_NAMES[dayOfCycle]} {this.formatTime()}
        </div>

    }
}