import React from 'react'

const Sensor = ({ sensordata }) => {
    const danger = {
        color: "red"
    };
    const normal = {
        color: "green"
    };


    return (
        <div className={"container"}>
            <table class="table table-dark">

                <thead>
                <tr>
                    <th>Sensor ID</th>
                    <th>Floor Number</th>
                    <th>Room Number</th>
                    <th>Smoke Level</th>
                    <th>CO2 Level</th>
                    <th>Date</th>
                    <th>Time</th>
                </tr>
                </thead>
                <tbody>
                {sensordata.map((sensord) => (
                    <tr style={sensord.smokeLevel > 5 ? danger : normal}>
                        <td>{sensord.sensor.sensorid}</td>
                        <td>{sensord.sensor.floorNo}</td>
                        <td>{sensord.sensor.roomNo}</td>
                        <td style={sensord.smokeLevel > 5 ? danger : normal} >{sensord.smokeLevel}</td>
                        <td style={sensord.co2Level > 5 ? danger : normal}>{sensord.co2Level}</td>
                        <td>{sensord.date}</td>
                        <td>{sensord.time}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    )
};

export default Sensor
