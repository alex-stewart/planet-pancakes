import React, {Component} from 'react';
import { LineChart, Line, Tooltip, XAxis, YAxis } from 'recharts';
import {formatCycle, formatDateAndTime} from '../util/date-utils';

export default class PriceChart extends Component {

    render() {
        let resource = this.props.resource;
        let data = [];
        Object.keys(resource.priceHistory).sort().forEach(function (dateString) {
            data.push(
                {
                    date: Date.parse(dateString),
                    price: resource.priceHistory[dateString]
                }
            )
        });

        const dateTooltipLabelFormatter = function(timestamp) {
            let date = new Date(timestamp);
            return (
                <div>
                    {
                        formatDateAndTime(date)
                    }
                </div>
            )
        };

        return (
            <LineChart width={400} height={150} data={data}>
                <XAxis dataKey="date" tickFormatter={formatCycle}/>
                <YAxis/>
                <Line type="monotone" dataKey="price" stroke="#8884d8" dot={false} activeDot={false}/>
                <Tooltip labelFormatter={dateTooltipLabelFormatter}/>
            </LineChart>
        );
    }
}