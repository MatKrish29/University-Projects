import axios from "axios";
import React, { Component } from "react";
import "./project.css";

import "bootstrap-css-only/css/bootstrap.min.css";
import "mdbreact/dist/css/mdb.css";


import * as ReactBootStrap from "react-bootstrap";
import { Button } from "react-bootstrap";
import { UpdateProjectDetails } from "./UpdateProjectDetails";


class ProjectDetails extends Component {
  constructor(props) {
    super(props);
    this.state = {
      onGoing: 0,
      pending: 0,
      completed: 0,
      searchQuery: "",
      filterQuery: "High",
      data: [],
      dataToUpdate: [],
      updateProjectModalShow: false,
      client: "",
      address: "",
      email: "",
      contactno: "",
      solution_description: "",
      date_admitted: "",
      delivery_date: "",
      repolink: "",
      priority: "",
      is_html: false,
      is_react: false,
      is_nodejs: false,
      is_css: false,
      is_cplus: false,
      is_oracle: false,
      is_csharp: false,
      is_php: false,
      is_ruby: false,
      is_java: false,
      is_ml: false,
      is_python: false,
      is_mysql: false,
      is_angular: false,
      is_nosql: false,
      is_js: false,
      is_bootstrap: false,
    };
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
        this.setState({ data: getData });
        console.log(this.state.data);

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

        }
        this.setState({ loading: false });
      });
  }

  deleteProject(id) {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };
    axios
      .delete("http://127.0.0.1:8000/project/delete/" + id + "/", config)
      .then((response) => {
        if (response.data != null) {
          this.componentDidMount();
        }
      });
  }

  searchProject(searchQuery) {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };
    axios
      .get("http://127.0.0.1:8000/project/search/" + searchQuery + "/", config)
      .then((response) => {
        if (response.data != null) {
          this.setState({ data: response.data });
          console.log(response.data);
          this.forceUpdate();
        }
      })
      .catch((error) => {
        if (error.response) {
          // Request made and server responded
          console.log("response_data : " + error.response.data);
          console.log("response_status : " + error.response.status);
          console.log("response_headers : " + error.response.headers);

          if (error.response.status === 400) {
            this.setState({ error: "Invalid credientials" });
            this.setState({ data: [] });
          }
        }
        this.setState({ loading: false });
      });
  }

  filterProject(filterQuery) {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };
    axios
      .get("http://127.0.0.1:8000/project/filter/" + filterQuery + "/", config)
      .then((response) => {
        if (response.data != null) {
          this.setState({ data: response.data });
          console.log(response.data);
          this.forceUpdate();
        }
      })
      .catch((error) => {
        if (error.response) {
          // Request made and server responded
          console.log("response_data : " + error.response.data);
          console.log("response_status : " + error.response.status);
          console.log("response_headers : " + error.response.headers);

          if (error.response.status === 400) {
            this.setState({ error: "Invalid credientials" });
            this.setState({ data: [] });
          }
        }
        this.setState({ loading: false });
      });
  }

  getProjectToUpdate(id) {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };
    axios
      .get("http://127.0.0.1:8000/project/get/" + id + "/", config)
      .then((response) => {
        if (response.data != null) {
          this.setState({ dataToUpdate: response.data });
          console.log(response.data);
        }
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
        }
        this.setState({ loading: false });
      });
    this.setState({ updateProjectModalShow: true })
  }

  render() {
    let updateProjectModalClose = () =>
      this.setState({ updateProjectModalShow: false });
    const projects = this.state.data;
    const renderProjects = (projects) => {
      return (
        <tr key={projects.id}>
          {/* <td>{projects.id}</td> */}
          <td>{projects.client}</td>
          <td>{projects.address}</td>
          <td>{projects.email}</td>
          <td>{projects.contactno}</td>
          <td>{projects.solution_description}</td>
          <td>{projects.date_admitted}</td>
          <td>{projects.delivery_date}</td>
          <td>
            <Button
              variant="info"
              size="sm"
              onClick={() => this.getProjectToUpdate(projects.id)}
            >
              Edit
            </Button>
            <Button
              variant="danger"
              size="sm"
              onClick={() => this.deleteProject(projects.id)}
            >
              Delete
            </Button>
          </td>
        </tr>
      );
    };

    return (
      <div className="fform1">
        <h4>Projects</h4>
        <div className="Back">
          <div class="row">
            <div class="col">
              <div className="refreshWrapper">
                <i class="fa fa-retweet" onClick={() => this.componentDidMount()}></i>
              </div>
            </div>
            <div class="col-md-auto">
              <div className="searchWrapper">
                <input
                  type="text"
                  className="textField"
                  placeholder="Search"
                  value={this.state.searchQuery}
                  onChange={(e) => this.setState({ searchQuery: e.target.value })}
                  onChangeCapture={() => this.searchProject(this.state.searchQuery)}
                />
                <i class="fa fa-search" onClick={() => this.searchProject(this.state.searchQuery)}></i>
              </div>
            </div>
            <div class="col col-lg-2">
              <div className="filterWrapper">
                <select value={this.state.filterQuery} onChange={(e) => this.setState({ filterQuery: e.target.value })} className="filterOptions" name="priority">
                  <option>High</option>
                  <option>Medium</option>
                  <option>Low</option>
                </select>
                <i class="fa fa-filter" onClick={() => this.filterProject(this.state.filterQuery)}></i>
              </div>
            </div>
          </div>

          <div className="tableWrapper">
            <ReactBootStrap.Table striped bordered responsive size="sm">
              <thead>
                <tr>
                  {/* <th className='tableHead'>ID</th> */}
                  <th className="tableHead">Client</th>
                  <th className="tableHead">Address</th>
                  <th className="tableHead">Email</th>
                  <th className="tableHead">Contact Number</th>
                  <th className="tableHead">Solution</th>
                  <th className="tableHead">Start Date</th>
                  <th className="tableHead">End Date</th>
                  <th className="tableHead">Actions</th>
                </tr>
              </thead>
              <tbody className="tableBody">
                {projects.map(renderProjects)}
              </tbody>
            </ReactBootStrap.Table>

            <UpdateProjectDetails
              show={this.state.updateProjectModalShow}
              onHide={updateProjectModalClose}
              projectdata={this.state.dataToUpdate}
            />
          </div>
        </div>
      </div>
    );
  }
}
export default ProjectDetails;
