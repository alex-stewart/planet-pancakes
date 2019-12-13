import _ from "lodash";

const MILLISECONDS_IN_DAY = 86400000;
const EPOCH_START_YEAR = 300;
const DAYS_IN_YEAR = 364;
const DAYS_IN_CYCLE = 14;
const CYCLE_DAY_NAMES = [
    "Wonday", "Tooday", "Triday", "Forday", "Thiffday", "Ixday", "Kingsday",
    "Newday", "Nonday", "Shuhday", "Sheday", "Sharday", "Queensday", "Emperorday"
];

export const formatDate = (date) => {
    let days = date / MILLISECONDS_IN_DAY;
    let year = EPOCH_START_YEAR + Math.ceil(days / DAYS_IN_YEAR);
    let dayOfYear = Math.ceil(days % DAYS_IN_YEAR);
    let cycleOfYear = Math.ceil(dayOfYear / DAYS_IN_CYCLE);
    let dayOfCycle = Math.floor(dayOfYear % DAYS_IN_CYCLE);

    return year + "☼ " + cycleOfYear + "☾ " + CYCLE_DAY_NAMES[dayOfCycle];
};

export const formatCycle = (date) => {
    let days = date / MILLISECONDS_IN_DAY;
    let year = EPOCH_START_YEAR + Math.ceil(days / DAYS_IN_YEAR);
    let dayOfYear = Math.ceil(days % DAYS_IN_YEAR);
    let cycleOfYear = Math.ceil(dayOfYear / DAYS_IN_CYCLE);

    return year + "☼ " + cycleOfYear + "☾ ";
};

export const formatDateAndTime = (date) => {
    return formatDate(date) + " " + formatTime(date);
};

const formatTime = (date) => {
    let hours = _.padStart(date.getUTCHours(), 2, 0);
    let minutes = _.padStart(date.getUTCMinutes(), 2, 0);
    let seconds = _.padStart(date.getUTCSeconds(), 2, 0);
    return hours + ":" + minutes + ":" +seconds
};
