import React, {Component} from 'react';
import {CYCLE_DAY_NAMES, CYCLES_IN_YEAR, DAYS_IN_CYCLE, WEEKENDS, GameDate} from '../../util/GameDate';
import {HOLIDAYS} from './Holidays';
import {faStar} from "@fortawesome/free-solid-svg-icons";
import {Table, UncontrolledTooltip} from 'reactstrap';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

export default class Calendar extends Component {

    constructor(props) {
        super(props);
        this.state = {
            gameDate: GameDate.now()
        };
    }

    generateHeader() {
        let headerRow = [<th key={"calendar-header-year"}>{this.state.gameDate.yearAsString()}</th>];
        let dayNameRows = CYCLE_DAY_NAMES.map(function (day) {
            return <th key={"calendar-header-" + day}>{day}</th>
        });
        headerRow.push(dayNameRows);
        return headerRow;
    }

    generateDayCell = function (cycle, day) {
        let gameDate = this.state.gameDate;
        let cellStyles = "calendar-table-cell";

        if (WEEKENDS.includes(day)){
            cellStyles = cellStyles + " calendar-table-cell-weekend";
        } else if (cycle === gameDate.cycle && day === gameDate.dayOfCycle) {
            cellStyles = cellStyles + " calendar-table-cell-today";
        }

        let cellContent = "";
        let isHoliday = cycle in HOLIDAYS && day in HOLIDAYS[cycle];
        if (isHoliday) {
            let holiday = HOLIDAYS[cycle][day];
            let id = "holiday-" + cycle + "-" + day;
            cellContent = <div>
                <FontAwesomeIcon id={id} className={"calendar-holiday-icon"} icon={faStar}/>
                <UncontrolledTooltip target={id} placement={"right"}>{holiday}</UncontrolledTooltip>
            </div>
        }

        return <td key={"calendar-cell-" + cycle + "-" + day}
                   className={cellStyles}>{cellContent}</td>
    };

    generateBody() {
        let rows = [];
        for (let i = 0; i < CYCLES_IN_YEAR; i++) {
            let row = [<th key={"cycle-header-" + i}
                           className={"table-dark calendar-table-cell"}>{i + 1 + "☾"}</th>];

            for (let j = 0; j < DAYS_IN_CYCLE; j++) {
                row.push(this.generateDayCell(i, j))
            }
            rows.push(<tr key={"calendar-row-" + i}>{row}</tr>)
        }
        return rows;
    }

    render() {
        return (
            <div className={"pp-page"}>
                <Table>
                    <thead className={"thead-dark"}>
                    <tr key={"header-row"}>
                        {this.generateHeader()}
                    </tr>
                    </thead>
                    <tbody className={"calendar-table-body"}>
                    {this.generateBody()}
                    </tbody>
                </Table>
            </div>
        )
    }
}