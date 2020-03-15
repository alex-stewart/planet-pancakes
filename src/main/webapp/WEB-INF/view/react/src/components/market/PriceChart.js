import React, {Component} from 'react';
import {Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis} from 'recharts';
import {GameDate} from "../../util/GameDate";
import commaNumber from 'comma-number';

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

        const formatTooltipLabel = function (timestamp) {
            let gameDate = GameDate.fromTimestamp(timestamp);
            return (<span>{gameDate.toString()}</span>)
        };

        const formatCoins = function (coins) {
            return (
                <span>
                    {commaNumber(coins) + " Coins"}
                </span>
            )
        };

        return (
            <ResponsiveContainer id={'container-' + resource.resourceName}
                                 height={20}>
                <LineChart data={data}>
                    <XAxis dataKey="date"
                           hide={true}/>
                    <YAxis hide={true}
                           domain={['dataMin', 'dataMax']}/>
                    <Line type="stepAfter"
                          dataKey="price"
                          stroke="#8884d8"
                          dot={false}
                          activeDot={false}/>
                    <Tooltip labelFormatter={formatTooltipLabel}
                             formatter={formatCoins}/>
                </LineChart>
            </ResponsiveContainer>
        );
    }
}
