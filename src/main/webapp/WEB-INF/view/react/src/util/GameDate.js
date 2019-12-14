import _ from "lodash";

export const DAYS_IN_CYCLE = 14;
export const CYCLES_IN_YEAR = 26;
export const CYCLE_DAY_NAMES = [
    "Wonday", "Tooday", "Triday", "Forday", "Thiffday", "Ixday", "Kingsday",
    "Newday", "Nonday", "Shuhday", "Sheday", "Sharday", "Queensday", "Emperorday"
];

const MILLISECONDS_IN_DAY = 86400000;
const EPOCH_START_YEAR = 300;
const DAYS_IN_YEAR = CYCLES_IN_YEAR * DAYS_IN_CYCLE;

export class GameDate {

    constructor(date){
        this.date = date;
        this.days = date / MILLISECONDS_IN_DAY;
        this.year = EPOCH_START_YEAR + Math.ceil(this.days / DAYS_IN_YEAR);
        this.dayOfYear = Math.ceil(this.days % DAYS_IN_YEAR);
        this.cycle = Math.ceil(this.dayOfYear / DAYS_IN_CYCLE);
        this.day = CYCLE_DAY_NAMES[Math.floor(this.dayOfYear % DAYS_IN_CYCLE)];
    }

    toString() {
        return this.toStringWithoutTime() + " " + this.formatTime(this.date);
    }

    toStringWithoutTime() {
        return this.toStringWithoutDay() + " " + this.day;
    }

    toStringWithoutDay() {
        return this.year + "☼ " + this.cycle + "☾";
    }

    formatTime(date) {
        let hours = _.padStart(date.getUTCHours(), 2, 0);
        let minutes = _.padStart(date.getUTCMinutes(), 2, 0);
        return hours + ":" + minutes;
    };
}

GameDate.fromTimestamp = function(timestamp) {
    let date = new Date(timestamp);
    return new GameDate(date);
};

GameDate.now = function(){
    return new GameDate(new Date());
};

