import React, {Component} from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faQuestionCircle} from "@fortawesome/free-solid-svg-icons";
import {UncontrolledTooltip} from 'reactstrap';

export default class DateInfo extends Component {

    tooltipContent() {
        return (
            <div>
                Each 14 day cycle is a complete rotation of the inner islands around the Land of the Large, measured by
                the phases of the moon.
                Each 26 cycle year is a complete solar cycle, measured by the phases of the sun.
            </div>
        );
    }

    render() {
        return (
            <div>
                <FontAwesomeIcon id={"date-info-icon"}
                                 icon={faQuestionCircle}/>
                <UncontrolledTooltip target={"date-info-icon"}
                                     placement={"left"}
                                     hideArrow={true}
                                     className={"date-info-tooltip"}>
                    {this.tooltipContent()}
                </UncontrolledTooltip>
            </div>
        )
    }
}
