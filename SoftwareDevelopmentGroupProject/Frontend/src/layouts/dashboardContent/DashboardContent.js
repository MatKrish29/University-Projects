import React, { Component } from 'react';
import CanvasJSReact from '../../assets/canvasjs.react';
import '../dashboardContent/content.css'
import { MDBMask, MDBView, MDBContainer, MDBRow, MDBCol } from "mdbreact";
import Grey from '../../assets/grey.jpg';
import axios from "axios";


var CanvasJSChart = CanvasJSReact.CanvasJSChart;
var CanvasJS = CanvasJSReact.CanvasJS;

class DashboardContent extends Component {
  addSymbols(e) {
    var suffixes = ["", "K", "M", "B"];
    var order = Math.max(Math.floor(Math.log(e.value) / Math.log(1000)), 0);
    if (order > suffixes.length - 1)
      order = suffixes.length - 1;
    var suffix = suffixes[order];
    return CanvasJS.formatNumber(e.value / Math.pow(1000, order)) + suffix;
  }

  constructor(props) {
    super(props);
    this.state = {

      projectData: [],
      employeeData: [],
      onGoing: 0,
      pending: 0,
      completed: 0,
      html: 0,
      css: 0,
      js: 0,
      reactJs: 0,
      angular: 0,
      bootstrap: 0,
      python: 0,
      java: 0,
      csharp: 0,
      cplus: 0,
      php: 0,
      ruby: 0,
      mysql: 0,
      nosql: 0,
      oracle: 0,
    }
  }

  componentDidMount() {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };


    axios.all([
      axios
        .get("http://127.0.0.1:8000/project/get/", config),

      axios
        .get("http://127.0.0.1:8000/employee/get/", config)
    ])
      .then(axios.spread((projectResponse, employeeResponse) => {
        this.setState({ projectData: projectResponse.data });
        console.log(this.state.projectData);

        this.state.projectData.forEach((project) => {
          if (project.status === "OnGoing") {
            this.setState({ onGoing: this.state.onGoing + 1 });
          }
          else if (project.status === "Pending") {
            this.setState({ pending: this.state.pending + 1 });
          }
          else {
            this.setState({ completed: this.state.completed + 1 });
          }
        });

        this.setState({ employeeData: employeeResponse.data });
        console.log(this.state.employeeData);

        this.state.employeeData.forEach((employee) => {
          if (employee.is_bootstrap === true) {
            this.setState({ bootstrap: this.state.bootstrap + 1 });
          }
          if (employee.is_react === true) {
            this.setState({ reactJs: this.state.reactJs + 1 });
          }
          if (employee.is_angular === true) {
            this.setState({ angular: this.state.angular + 1 });
          }
          if (employee.is_java === true) {
            this.setState({ java: this.state.java + 1 });
          }
          if (employee.is_python === true) {
            this.setState({ python: this.state.python + 1 });
          }
          if (employee.is_ruby === true) {
            this.setState({ ruby: this.state.ruby + 1 });
          }
          if (employee.is_csharp === true) {
            this.setState({ csharp: this.state.csharp + 1 });
          }
          if (employee.is_php === true) {
            this.setState({ php: this.state.php + 1 });
          }
        });
      }));
  }

  render() {
    const options = {
      animationEnabled: true,
      theme: "dark2",
      title: {
        text: "Technology Skills in Inertia"
      },
      axisX: {
        title: "Skills",
        reversed: true,
      },
      axisY: {
        title: "Employees",
        labelFormatter: this.addSymbols
      },
      data: [{
        type: "bar",
        dataPoints: [
          { y: this.state.reactJs, label: "ReactJS" },
          { y: this.state.angular, label: "Angular" },
          { y: this.state.bootstrap, label: "Bootstrap" },
          { y: this.state.java, label: "Java" },
          { y: this.state.python, label: "Python" },
          { y: this.state.php, label: "PHP" },
          { y: this.state.ruby, label: "Ruby" },
          { y: this.state.csharp, label: "C#" },
        ]
      }]
    }

    return (
      <div className="mainContainer">
        <MDBContainer >
          <MDBRow>
            <MDBCol>
              <MDBView waves>
                <img
                  src={Grey}
                  className="img-fluid rounded-pill"
                  alt=""
                />
                <MDBMask className="flex-center" >
                  <p className="textCount">{this.state.onGoing}  <blockquote> On Going Projects  </blockquote></p>
                </MDBMask>
              </MDBView>
            </MDBCol>
            <MDBCol>
              <MDBView waves>
                <img
                  src={Grey}
                  className="img-fluid rounded-pill"
                  alt=""
                />
                <MDBMask className="flex-center" >
                  <p className="textCount">{this.state.pending}  <blockquote> Pending Projects </blockquote></p>
                </MDBMask>
              </MDBView>
            </MDBCol>
            <MDBCol>
              <MDBView waves>
                <img
                  src={Grey}
                  className="img-fluid rounded-pill"
                  alt=""
                />
                <MDBMask className="flex-center" >
                  <p className="textCount">{this.state.completed}  <blockquote> Completed Projects  </blockquote></p>
                </MDBMask>
              </MDBView>
            </MDBCol>
          </MDBRow>
          <MDBRow h="">
            <MDBCol md="12">
              <div className="h-50">
                <CanvasJSChart options={options} />
              </div>
            </MDBCol>
          </MDBRow>
        </MDBContainer>

      </div>
    );
  }
}
export default DashboardContent;