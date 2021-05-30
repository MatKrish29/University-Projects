import axios from "axios";
import { Component } from "react";
import "../employees/EmployeeCss.css";
import "bootstrap/dist/css/bootstrap.min.css";
import UserIcon from "../../assets/images/userIcon.png";
import UpdateEmployeeDetails from "./UpdateEmployeeDetails";

class EmployeeDetails extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      //show: false,
      detail: [],
      employeeDataToUpdate: [],
      updateEmployeeModalShow: false,
      searchQuery: "",
      filterQuery: "Frontend Level-1",
      firstname: "",
      lastname: "",
      email: "",
      designation: "",
      department: "",
      grade: "",
      specialization: "",
      type: "",
      experience: "",
      is_html: false,
      is_analytical: false,
      is_react: false,
      is_nodejs: false,
      is_teamwork: false,
      is_passion: false,
      is_css: false,
      is_cplus: false,
      is_oracle: false,
      is_csharp: false,
      is_php: false,
      is_ruby: false,
      is_java: false,
      is_ml: false,
      is_creativity: false,
      is_python: false,
      is_mysql: false,
      is_leadership: false,
      is_angular: false,
      is_nosql: false,
      is_communication: false,
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
      .get("http://127.0.0.1:8000/employee/get/", config)
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
          // Something happened in setting up the request that triggered an Error
          this.setState({
            error: "Something went wrong. Please try again later.",
          });
        }
        this.setState({ loading: false });
      });
  }

  deleteEmployee(id) {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };
    axios
      .delete("http://127.0.0.1:8000/employee/delete/" + id + "/", config)
      .then((response) => {
        if (response.data != null) {
          this.componentDidMount();

        }
      });
  }

  filterEmployee(filterQuery) {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };
    axios
      .get("http://127.0.0.1:8000/employee/filter/" + filterQuery + "/", config)
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

  searchEmployee(searchQuery) {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };
    axios
      .get("http://127.0.0.1:8000/employee/search/" + searchQuery + "/", config)
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

  getId(id) {
    let empId = id;
    console.log(empId);
    this.setState({ updateEmployeeModalShow: true });
  }

  getEmployeeToUpdate(id) {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };
    axios
      .get("http://127.0.0.1:8000/employee/get/" + id + "/", config)
      .then((response) => {
        if (response.data != null) {
          this.setState({ employeeDataToUpdate: response.data });
          console.log(response);
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
    this.setState({ updateEmployeeModalShow: true })
  }

  render() {
    let updateEmployeeModalClose = () =>
      this.setState({ updateEmployeeModalShow: false });

    var renderedOutput = this.state.data.map((user) => (
      <div className="col" key={user.id} >

        <div className="card h-100" >
          <div className="card-header" >
            <i class="fa fa-wrench" onClick={() => this.getEmployeeToUpdate(user.id)}></i>
          </div>
          <img className="userImage" alt="img" src={UserIcon} />
          <div className="card-body">
            <h5 className="card-title">{user.firstname}</h5>
            <p className="card-text">Email: {user.email}</p>
            <p className="card-text">Type: {user.type}</p>
            <p className="card-text">Grade: {user.grade}</p>
            <p className="card-text">Specialization: {user.specialization}</p>
            <i class="fa fa-trash" onClick={() => this.deleteEmployee(user.id)}></i>
          </div>
        </div>
      </div>
    ));
    return (
      <div className="fform1">
        <h4>Employees</h4>
        <div className="Back">
          <div class="row">
            <div class="col">
              <div className="refreshWrapperEmployee">
                <i class="fa fa-retweet" onClick={() => this.componentDidMount()}></i>
              </div>
            </div>
            <div class="col-md-auto">
              <div className="searchWrapperEmployee">
                <input
                  type="text"
                  className="textField"
                  placeholder="Search"
                  value={this.state.searchQuery}
                  onChange={(e) => this.setState({ searchQuery: e.target.value })}
                />
                <i class="fa fa-search" onClick={() => this.searchEmployee(this.state.searchQuery)}></i>
              </div>
            </div>
            <div class="col col-lg-2">
              <div className="sortWrapperEmployee">
                <select value={this.state.filterQuery}
                  onChange={(e) => this.setState({ filterQuery: e.target.value })}
                  className="filterOptions"
                  name="priority">
                  <option>Frontend Level-1</option>
                  <option>Frontend Level-2</option>
                  <option>Frontend Level-3</option>
                  <option>Backend Level-1</option>
                  <option>Backend Level-2</option>
                  <option>Backend Level-3</option>
                  <option>Full-Stack Level-1</option>
                  <option>Full-Stack Level-2</option>
                  <option>Full-Stack Level-3</option>
                </select>
                <i class="fa fa-filter" onClick={() => this.filterEmployee(this.state.filterQuery)}></i>
              </div>
            </div>
          </div>
          <div className="row row-cols-1 row-cols-md-4 g-3">
            {renderedOutput}
          </div>
        </div>
        <UpdateEmployeeDetails
          show={this.state.updateEmployeeModalShow}
          onHide={updateEmployeeModalClose}
          employeedata={this.state.employeeDataToUpdate}
        />
      </div>
    );
  }
}
export default EmployeeDetails;
