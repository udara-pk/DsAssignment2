// src/App.js

import React, {Component} from 'react';
import SensorWeb from './components/sensorWeb';



class App extends Component {

  state = {
    sensordata: []
  }

  getItems() {
    fetch('http://localhost:8080/rest/sensordata/all')
    .then(res => res.json())
    .then((data) => {
      this.setState({ sensordata: data })
      console.log(data);
    })
    .catch(console.log)
  }

  componentDidMount() {
    this.getItems();
    this.timer = setInterval(()=> this.getItems(), 40000);
  }
  render () {
    return (<SensorWeb sensordata={this.state.sensordata} />);
  }
}

export default App;
