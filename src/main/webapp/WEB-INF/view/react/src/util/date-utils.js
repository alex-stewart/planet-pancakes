const EPOCH_START_YEAR = 300;
const CYCLES_IN_YEAR = 26;
const DAYS_IN_CYCLE = 14;
const CYCLE_DAY_NAMES = [
    "Wonday", "Tooday", "Triday", "Forday", "Thiffday", "Ixday", "Kingsday",
    "Newday", "Nonday", "Shuhday", "Sheday", "Sharday", "Queensday", "Emperorday"
];

export const formatDate = (date) => {
    let days = date / 1000 / 60 / 60 / 24;
    let year = EPOCH_START_YEAR + Math.ceil(days / (CYCLES_IN_YEAR * DAYS_IN_CYCLE));
    let dayOfYear = Math.ceil(days % (CYCLES_IN_YEAR * DAYS_IN_CYCLE));
    let cycleOfYear = Math.ceil(dayOfYear / DAYS_IN_CYCLE);
    let dayOfCycle = Math.floor(dayOfYear % DAYS_IN_CYCLE);

    return year + "y " + cycleOfYear + "c " + CYCLE_DAY_NAMES[dayOfCycle];
};

export const formatCycle = (date) => {
    let days = date / 1000 / 60 / 60 / 24;
    let year = EPOCH_START_YEAR + Math.ceil(days / (CYCLES_IN_YEAR * DAYS_IN_CYCLE));
    let dayOfYear = Math.ceil(days % (CYCLES_IN_YEAR * DAYS_IN_CYCLE));
    let cycleOfYear = Math.ceil(dayOfYear / DAYS_IN_CYCLE);

    return year + "y " + cycleOfYear + "c ";
};

export const formatDateAndTime = (date) => {
    return formatDate(date)  + " " + date.toLocaleTimeString('en-GB', {hour12: false});
};
