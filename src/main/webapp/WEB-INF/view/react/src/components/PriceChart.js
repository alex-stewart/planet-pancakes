import React, {Component} from 'react';
import {Chart} from 'react-charts'
import _ from "lodash";

export default class PriceChart extends Component {

    render() {
        const axes =[
                { primary: true, type: 'utc', position: 'bottom' },
                { type: 'linear', position: 'left' }
            ];

        let resource = this.props.resource;

        let priceHistory = [];
        Object.keys(resource.priceHistory).sort().forEach(function(dateString) {
            let date = Date.parse(dateString);
            priceHistory[date] = resource.priceHistory[dateString];
        });

        const series = {
            showPoints: false
        };

        const data = [
            {
                label: resource.resourceName,
                data: _.toPairs(priceHistory)
            }
        ];

        return <div style={{width: '400px', height: '150px'}}>
            <Chart data={data} axes={axes} series={series} tooltip/>
        </div>
    }
}