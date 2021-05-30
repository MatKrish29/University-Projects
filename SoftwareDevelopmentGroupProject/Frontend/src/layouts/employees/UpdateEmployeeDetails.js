import React, { Component } from 'react';
import { Form, Button, Modal } from 'react-bootstrap';
import AddEmployee from './AddEmployee';
import axios from "axios";
import "./FormCss.css";

export default class UpdateEmployeeDetails extends Component {

  constructor(props) {
    super(props);
    this.state = {
      projectDetails: [],
      selectedItem: "",
      project: "None",
      employeeId: 0,
    }
  }

  componentDidMount() {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };

    axios
      .get("http://127.0.0.1:8000/project/get/", config)
      .then((response) => {
        const getData = response.data;
        this.setState({ projectDetails: getData });
        console.log(this.state.projectDetails);

        setTimeout(() => {
          this.setState({ loading: false });
        }, 4000);
      })
      .catch((error) => {
        if (error.response) {
          // Request made and server responded
          console.log("response_data : " + error.response.data);
          console.log("response_status : " + error.response.status);
          console.log("response_headers : " + error.response.headers);

          if (error.response.status === 400) {
            this.setState({ error: "Invalid credientials" });
          }
        } else if (error.request) {
          // The request was made but no response was received
          this.setState({ error: "Server is inactive" });
        } else {
          // Something happened in setting up the request that triggered an Error
          this.setState({
            error: "Something went wrong. Please try again later.",
          });
        }
        this.setState({ loading: false });
      });
  }

  assignEmployeeProject() {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };
    var id = 0;
    var employee = {};
    this.props.employeedata.map((data) => {
      id = data.id;
      employee = {
        firstname: data.firstname,
        lastname: data.lastname,
        email: data.email,
        designation: data.designation,
        department: data.department,
        experience: data.experience,
        grade: data.grade,
        specialization: data.specialization,
        type: data.type,
        is_html: data.is_html,
        is_analytical: data.is_analytical,
        is_react: data.is_react,
        is_teamwork: data.is_teamwork,
        is_passion: data.is_passion,
        is_css: data.is_css,
        is_cplus: data.is_cplus,
        is_oracle: data.is_oracle,
        is_csharp: data.is_csharp,
        is_php: data.is_php,
        is_ruby: data.is_ruby,
        is_java: data.is_java,
        is_creativity: data.is_creativity,
        is_python: data.is_python,
        is_mysql: data.is_mysql,
        is_leadership: data.is_leadership,
        is_angular: data.is_angular,
        is_nosql: data.is_nosql,
        is_communication: data.is_communication,
        is_js: data.is_js,
        is_bootstrap: data.is_bootstrap,
        projects: this.state.selectedItem
      }
    });
    axios
      .put("http://127.0.0.1:8000/employee/assign/project/" + id + "/", employee, config)
      .then((response) => {
        if (response.data != null) {
          console.log(this.props.employeedata);
          console.log(response.data);
        }
      });
  }

  deleteEmployeeProject() {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };
    var id = 0;
    var employee = {};
    this.props.employeedata.map((data) => {
      id = data.id;
      employee = {
        firstname: data.firstname,
        lastname: data.lastname,
        email: data.email,
        designation: data.designation,
        department: data.department,
        experience: data.experience,
        grade: data.grade,
        specialization: data.specialization,
        type: data.type,
        is_html: data.is_html,
        is_analytical: data.is_analytical,
        is_react: data.is_react,
        is_teamwork: data.is_teamwork,
        is_passion: data.is_passion,
        is_css: data.is_css,
        is_cplus: data.is_cplus,
        is_oracle: data.is_oracle,
        is_csharp: data.is_csharp,
        is_php: data.is_php,
        is_ruby: data.is_ruby,
        is_java: data.is_java,
        is_creativity: data.is_creativity,
        is_python: data.is_python,
        is_mysql: data.is_mysql,
        is_leadership: data.is_leadership,
        is_angular: data.is_angular,
        is_nosql: data.is_nosql,
        is_communication: data.is_communication,
        is_js: data.is_js,
        is_bootstrap: data.is_bootstrap,
        projects: null
      }
    });
    axios
      .put("http://127.0.0.1:8000/employee/assign/project/" + id + "/", employee, config)
      .then((response) => {
        if (response.data != null) {
          console.log(this.props.employeedata);
          console.log(response.data);
        }
      });
    this.componentDidMount();
  }

  render() {
    return (
      <div className="App">
        <Modal {...this.props}
          dialogClassName="custom-dialog"
          aria-labelledby="example-custom-modal-styling-title">
          <Modal.Header closeButton>
            <Modal.Title id="contained-modal-title-vcenter">
              Update Employee
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <div className="updateEmployeeInterface">
              <div className="employeeProject">
                <Form.Group>
                  <label className="label" id="labelProjectOnUpdateEmployee">Projects</label>
                  <div className="input-group">
                    <select className="selectionUpdateEmployee">
                      {this.props.employeedata.map((employee) => (
                        <option
                          key={employee.id}
                          value={employee.projects}
                        >
                          {employee.projects === null ? this.state.project : employee.projects}
                        </option>
                      ))}
                    </select>
                    <div class="input-group-append">
                      <button className="buttonOnUpdateEmployee" type="button" onClick={() => this.deleteEmployeeProject()}>Delete</button>
                    </div>
                  </div>
                </Form.Group>
                <Form.Group controlId="formBasicProject">
                  <label className="label" for="exampleFormControlSelect1">Add Project</label>
                  <div class="input-group">
                    <select className="selectionUpdateEmployee" value={this.state.selectedTeam}
                      onChange={e =>
                        this.setState({
                          selectedItem: e.target.value,
                        })
                      }>
                      {this.state.projectDetails.map(project => (
                        <option
                          key={project.id}
                          value={project.solution_description}
                        >
                          {project.solution_description}
                        </option>
                      ))}
                    </select>
                    <div class="input-group-append">
                      <button className="buttonOnUpdateEmployee" type="button" onClick={() => this.assignEmployeeProject()}>Add</button>
                    </div>
                  </div>
                </Form.Group>
              </div>
              {this.props.employeedata.map((data) => (
                <AddEmployee
                  key={data.id}
                  id={data.id}
                  firstname={data.firstname}
                  lastname={data.lastname}
                  email={data.email}
                  designation={data.designation}
                  department={data.department}
                  experience={data.experience}
                  grade={data.grade}
                  specialization={data.specialization}
                  type={data.type}
                  is_html={data.is_html}
                  is_analytical={data.is_analytical}
                  is_react={data.is_react}
                  is_nodejs={data.is_nodejs}
                  is_teamwork={data.is_teamwork}
                  is_passion={data.is_passion}
                  is_css={data.is_css}
                  is_cplus={data.is_cplus}
                  is_oracle={data.is_oracle}
                  is_csharp={data.is_csharp}
                  is_php={data.is_php}
                  is_ruby={data.is_ruby}
                  is_java={data.is_java}
                  is_ml={data.is_ml}
                  is_creativity={data.is_creativity}
                  is_python={data.is_python}
                  is_mysql={data.is_mysql}
                  is_leadership={data.is_leadership}
                  is_angular={data.is_angular}
                  is_nosql={data.is_nosql}
                  is_communication={data.is_communication}
                  is_js={data.is_js}
                  is_bootstrap={data.is_bootstrap}
                  updateison={true}
                />
              ))}
            </div>
          </Modal.Body>
          <Modal.Footer>
            <Button onClick={this.props.onHide}>CLOSE</Button>
          </Modal.Footer>
        </Modal>
      </div>
    );
  }
}

