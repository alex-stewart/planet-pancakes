import _ from "lodash";

export const MILLISECONDS_IN_DAY = 86400000;
export const EPOCH_START_YEAR = 300;
export const DAYS_IN_YEAR = 364;
export const DAYS_IN_CYCLE = 14;
export const CYCLE_DAY_NAMES = [
    "Wonday", "Tooday", "Triday", "Forday", "Thiffday", "Ixday", "Kingsday",
    "Newday", "Nonday", "Shuhday", "Sheday", "Sharday", "Queensday", "Emperorday"
];

export class GameDate {

    constructor(date){
        this.date = date;
        this.days = date / MILLISECONDS_IN_DAY;
        this.year = EPOCH_START_YEAR + Math.ceil(this.days / DAYS_IN_YEAR);
        this.dayOfYear = Math.ceil(this.days % DAYS_IN_YEAR);
        this.cycleOfYear = Math.ceil(this.dayOfYear / DAYS_IN_CYCLE);
        this.dayOfCycle = Math.floor(this.dayOfYear % DAYS_IN_CYCLE);
    }

    toString() {
        return this.toStringWithoutTime() + " " + this.formatTime(this.date);
    }

    toStringWithoutTime() {
        return this.toStringWithoutDay() + " " + CYCLE_DAY_NAMES[this.dayOfCycle];
    }

    toStringWithoutDay() {
        return this.year + "☼ " + this.cycleOfYear + "☾";
    }

    formatTime(date) {
        let hours = _.padStart(date.getUTCHours(), 2, 0);
        let minutes = _.padStart(date.getUTCMinutes(), 2, 0);
        let seconds = _.padStart(date.getUTCSeconds(), 2, 0);
        return hours + ":" + minutes + ":" +seconds
    };


}

