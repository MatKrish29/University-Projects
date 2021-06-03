import React, { Component } from "react";
import "../employees/EmployeeCss.css";
import "bootstrap-css-only/css/bootstrap.min.css";
import "mdbreact/dist/css/mdb.css";
import axios from "axios";
import { EmployeePotentialViewModal } from "./EmployeePotentialViewModal";
import { Button } from 'react-bootstrap';
import UpdateEmployeeDetails from "./UpdateEmployeeDetails";

const emailRegex = RegExp(
  /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
);

const formValid = (formErrors) => {
  let valid = true;
  Object.values(formErrors).forEach((val) => {
    val.length > 0 && (valid = false);
  });
  return valid;
};

class AddEmployee extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isHtml_checked: this.props.updateison ? this.props.is_html : false,
      isCss_checked: this.props.updateison ? this.props.is_css : false,
      isReact_checked: this.props.updateison ? this.props.is_react : false,
      isCplus_checked: this.props.updateison ? this.props.is_cplus : false,
      isOracle_checked: this.props.updateison ? this.props.is_oracle : false,
      isChash_checked: this.props.updateison ? this.props.is_csharp : false,
      isPhp_checked: this.props.updateison ? this.props.is_php : false,
      isRuby_checked: this.props.updateison ? this.props.is_ruby : false,
      isJava_checked: this.props.updateison ? this.props.is_java : false,
      isPython_checked: this.props.updateison ? this.props.is_python : false,
      isMysql_checked: this.props.updateison ? this.props.is_mysql : false,
      isAngular_checked: this.props.updateison ? this.props.is_angular : false,
      isNosql_checked: this.props.updateison ? this.props.is_nosql : false,
      isJs_checked: this.props.updateison ? this.props.is_js : false,
      isBootstrap_checked: this.props.updateison ? this.props.is_bootstrap : false,
      isAnalytical_checked: this.props.updateison ? this.props.is_analytical : false,
      isTeamwork_checked: this.props.updateison ? this.props.is_teamwork : false,
      isCommunication_checked: this.props.updateison ? this.props.is_communication : false,
      isCreativity_checked: this.props.updateison ? this.props.is_creativity : false,
      isPassion_checked: this.props.updateison ? this.props.is_passion : false,
      isLeadership_checked: this.props.updateison ? this.props.is_leadership : false,
      count_checked: 0,
      employeePotential: [],
      employeePotentialModalShow: false,
      buttondisabled: false,
      employeeParams: {
        firstname: this.props.updateison ? this.props.firstname : "",
        lastname: this.props.updateison ? this.props.lastname : "",
        email: this.props.updateison ? this.props.email : "",
        designation: this.props.updateison ? this.props.designation : "",
        department: this.props.updateison ? this.props.department : "",
        grade: this.props.updateison ? this.props.grade : "",
        specialization: this.props.updateison ? this.props.specialization : "",
        type: this.props.updateison ? this.props.type : "",
        experience: this.props.updateison ? this.props.experience : "",
      },
      formErrors: {
        firstname: "",
        lastname: "",
        email: "",
        designation: "",
        department: "",
        experience: "",
      },
      changedisplay: false,
    };
  }

  componentDidMount() {
    if (this.props.updateison) {
      this.setState({ buttondisabled: true });
    }
  }

  changeTick = (event) => {
    this.setState({ isHtml_checked: !this.state.isHtml_checked });
    this.setState({ isCss_checked: !this.state.isCss_checked });
    this.setState({ isJs_checked: !this.state.isJs_checked });
  }
  reactChangeHandler = (event) => {
    this.setState({ isReact_checked: !this.state.isReact_checked });
    if (this.state.isAngular_checked === false) {
      this.setState({ isHtml_checked: !this.state.isHtml_checked });
      this.setState({ isCss_checked: !this.state.isCss_checked });
      this.setState({ isJs_checked: !this.state.isJs_checked });
    }
  };
  cplusChangeHandler = (event) => {
    this.setState({ isCplus_checked: !this.state.isCplus_checked });

    if (this.state.isCplus_checked === true && this.state.isJava_checked === false && this.state.isPython_checked === false && this.state.isPhp_checked === false && this.state.isRuby_checked === false && this.state.isChash_checked === false) {
      this.setState({ isOracle_checked: false });
      this.setState({ isMysql_checked: false });
      this.setState({ isNosql_checked: false });
    }
  };
  oracleChangeHandler = (event) => {
    if (this.state.isJava_checked === true || this.state.isPython_checked === true || this.state.isCplus_checked === true || this.state.isPhp_checked === true || this.state.isRuby_checked === true || this.state.isChash_checked === true) {
      this.setState({ isOracle_checked: !this.state.isOracle_checked });
    } else this.setState({ isOracle_checked: false });


  };
  chashChangeHandler = (event) => {
    this.setState({ isChash_checked: !this.state.isChash_checked });

    if (this.state.isCplus_checked === false && this.state.isJava_checked === false && this.state.isPython_checked === false && this.state.isPhp_checked === false && this.state.isRuby_checked === false && this.state.isChash_checked === true) {
      this.setState({ isOracle_checked: false });
      this.setState({ isMysql_checked: false });
      this.setState({ isNosql_checked: false });
    }
  };
  phpChangeHandler = (event) => {
    this.setState({ isPhp_checked: !this.state.isPhp_checked });

    if (this.state.isCplus_checked === false && this.state.isJava_checked === false && this.state.isPython_checked === false && this.state.isPhp_checked === true && this.state.isRuby_checked === false && this.state.isChash_checked === false) {
      this.setState({ isOracle_checked: false });
      this.setState({ isMysql_checked: false });
      this.setState({ isNosql_checked: false });
    }
  };
  rubyChangeHandler = (event) => {
    this.setState({ isRuby_checked: !this.state.isRuby_checked });

    if (this.state.isCplus_checked === false && this.state.isJava_checked === false && this.state.isPython_checked === false && this.state.isPhp_checked === false && this.state.isRuby_checked === true && this.state.isChash_checked === false) {
      this.setState({ isOracle_checked: false });
      this.setState({ isMysql_checked: false });
      this.setState({ isNosql_checked: false });
    }
  };
  javaChangeHandler = (event) => {
    this.setState({ isJava_checked: !this.state.isJava_checked });

    if (this.state.isCplus_checked === false && this.state.isJava_checked === true && this.state.isPython_checked === false && this.state.isPhp_checked === false && this.state.isRuby_checked === false && this.state.isChash_checked === false) {
      this.setState({ isOracle_checked: false });
      this.setState({ isMysql_checked: false });
      this.setState({ isNosql_checked: false });
    }
  };
  pythonChangeHandler = (event) => {
    this.setState({ isPython_checked: !this.state.isPython_checked });

    if (this.state.isCplus_checked === false && this.state.isJava_checked === false && this.state.isPython_checked === true && this.state.isPhp_checked === false && this.state.isRuby_checked === false && this.state.isChash_checked === false) {
      this.setState({ isOracle_checked: false });
      this.setState({ isMysql_checked: false });
      this.setState({ isNosql_checked: false });
    }
  };
  mysqlChangeHandler = (event) => {
    if (this.state.isJava_checked === true || this.state.isPython_checked === true || this.state.isCplus_checked === true || this.state.isPhp_checked === true || this.state.isRuby_checked === true || this.state.isChash_checked === true) {
      this.setState({ isMysql_checked: !this.state.isMysql_checked });
    } else this.setState({ isMysql_checked: false });
  };
  angularChangeHandler = (event) => {
    this.setState({ isAngular_checked: !this.state.isAngular_checked });
    if (this.state.isReact_checked === false) {
      this.setState({ isHtml_checked: !this.state.isHtml_checked });
      this.setState({ isCss_checked: !this.state.isCss_checked });
      this.setState({ isJs_checked: !this.state.isJs_checked });
    }
  };
  nosqlChangeHandler = (event) => {
    if (this.state.isJava_checked === true || this.state.isPython_checked === true || this.state.isCplus_checked === true || this.state.isPhp_checked === true || this.state.isRuby_checked === true || this.state.isChash_checked === true) {
      this.setState({ isNosql_checked: !this.state.isNosql_checked });
    } else this.setState({ isNosql_checked: false });

  };
  bootstrapChangeHandler = (event) => {
    this.setState({ isBootstrap_checked: !this.state.isBootstrap_checked });
  };
  analyticalChangeHandler = (event) => {
    this.setState({ isAnalytical_checked: !this.state.isAnalytical_checked });
  };
  creativityChangeHandler = (event) => {
    this.setState({ isCreativity_checked: !this.state.isCreativity_checked });
  };
  teamworkChangeHandler = (event) => {
    this.setState({ isTeamwork_checked: !this.state.isTeamwork_checked });
  };
  passionChangeHandler = (event) => {
    this.setState({ isPassion_checked: !this.state.isPassion_checked });
  };
  communicationChangeHandler = (event) => {
    this.setState({ isCommunication_checked: !this.state.isCommunication_checked });
  };
  leadershipChangeHandler = (event) => {
    this.setState({ isLeadership_checked: !this.state.isLeadership_checked });
  };

  changeHandler = (event) => {
    const { name, value } = event.target;
    let formErrors = this.state.formErrors;

    switch (name) {
      case "firstname":
        if (value.length === 0) {
          formErrors.firstname =
            value.length === 0 ? "Cannot leave this field empty" : "";
        } else {
          formErrors.firstname =
            value.length < 3 ? "minimum 3 characters required" : "";
        }
        break;

      case "lastname":
        if (value.length === 0) {
          formErrors.lastname =
            value.length === 0 ? "Cannot leave this field empty" : "";
        } else {
          formErrors.lastname =
            value.length < 3 ? "minimum 3 characters required" : "";
        }
        break;

      case "email":
        if (value.length === 0) {
          formErrors.email =
            value.length === 0 ? "Cannot leave this field empty" : "";
        } else {
          formErrors.email = emailRegex.test(value)
            ? ""
            : "Invalid email address";
        }
        break;

      case "designation":
        if (value.length === 0) {
          formErrors.designation =
            value.length === 0 ? "Cannot leave this field empty" : "";
        } else {
          formErrors.designation =
            value.length < 3 ? "minimum 3 characters required" : "";
        }
        break;

      case "department":
        formErrors.department =
          value.length === 0 ? "Cannot leave this field empty" : "";
        break;

      case "experience":
        formErrors.experience =
          value.length === 0 ? "Cannot leave this field empty" : "";
        break;
      default:
        break;
    }
    let employeeParamsNew = { ...this.state.employeeParams };
    let val = event.target.value;

    employeeParamsNew[event.target.name] = val;
    this.setState({
      employeeParams: employeeParamsNew,
    });
  };

  clickHandler = (event) => {
    this.state.count_checked = 0;

    if (this.state.isHtml_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
    }
    if (this.state.isReact_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
    }
    if (this.state.isCss_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
    }
    if (this.state.isCplus_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
    }
    if (this.state.isOracle_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
    }
    if (this.state.isChash_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
    }
    if (this.state.isPhp_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
    }
    if (this.state.isRuby_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
    }
    if (this.state.isJava_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
    }
    if (this.state.isPython_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
    }
    if (this.state.isMysql_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
    }
    if (this.state.isAngular_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
    }
    if (this.state.isNosql_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
    }
    if (this.state.isJs_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
    }
    if (this.state.isBootstrap_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
    }

    console.log(this.state.count_checked);
    const user = {
      firstname: this.state.employeeParams.firstname,
      lastname: this.state.employeeParams.lastname,
      email: this.state.employeeParams.email,
      designation: this.state.employeeParams.designation,
      department: this.state.employeeParams.department,
      experience: this.state.employeeParams.experience,
      is_html: this.state.employeeParams.is_html,
      is_react: this.state.employeeParams.is_react,
      is_analytical: this.state.employeeParams.is_analytical,
      is_teamwork: this.state.employeeParams.is_teamwork,
      is_passion: this.state.employeeParams.is_passion,
      is_css: this.state.employeeParams.is_css,
      is_cplus: this.state.employeeParams.is_cplus,
      is_oracle: this.state.employeeParams.is_oracle,
      is_csharp: this.state.employeeParams.is_csharp,
      is_php: this.state.employeeParams.is_php,
      is_ruby: this.state.employeeParams.is_ruby,
      is_java: this.state.employeeParams.is_java,
      is_creativity: this.state.employeeParams.is_creativity,
      is_python: this.state.employeeParams.is_python,
      is_mysql: this.state.employeeParams.is_mysql,
      is_leadership: this.state.employeeParams.is_leadership,
      is_angular: this.state.employeeParams.is_angular,
      is_nosql: this.state.employeeParams.is_nosql,
      is_communication: this.state.employeeParams.is_communication,
      is_js: this.state.employeeParams.is_js,
      is_bootstrap: this.state.employeeParams.is_bootstrap,
    };

    if (formValid(this.state.formErrors)) {
      if (
        user.firstname === "" ||
        user.lastname === "" ||
        user.email === "" ||
        user.designation === "" ||
        user.department === ""

        // user.experience === ""
      ) {
        alert("Fill out all fields!");
      } else if (this.state.count_checked < 3) {
        alert("Minimum three skills needed.");
      } else {
        this.employee();
        this.setState({ employeePotentialModalShow: true });
      }
    } else {
      alert("Form Invalid");
    }
  };

  employee = (event) => {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };

    const employee = {
      firstname: this.state.employeeParams.firstname,
      lastname: this.state.employeeParams.lastname,
      email: this.state.employeeParams.email,
      designation: this.state.employeeParams.designation,
      department: this.state.employeeParams.department,
      grade: this.state.employeeParams.grade,
      specialization: this.state.employeeParams.specialization,
      type: this.state.employeeParams.type,
      experience: this.state.employeeParams.experience,
      is_html: this.state.isHtml_checked,
      is_react: this.state.isReact_checked,
      is_analytical: this.state.isAnalytical_checked,
      is_teamwork: this.state.isTeamwork_checked,
      is_passion: this.state.isPassion_checked,
      is_css: this.state.isCss_checked,
      is_cplus: this.state.isCplus_checked,
      is_oracle: this.state.isOracle_checked,
      is_csharp: this.state.isChash_checked,
      is_php: this.state.isPhp_checked,
      is_ruby: this.state.isRuby_checked,
      is_java: this.state.isJava_checked,
      is_creativity: this.state.isCreativity_checked,
      is_python: this.state.isPython_checked,
      is_mysql: this.state.isMysql_checked,
      is_leadership: this.state.isLeadership_checked,
      is_angular: this.state.isAngular_checked,
      is_nosql: this.state.isNosql_checked,
      is_communication: this.state.isCommunication_checked,
      is_js: this.state.isJs_checked,
      is_bootstrap: this.state.isBootstrap_checked,
    };
    console.log(employee);
    axios
      .post("http://127.0.0.1:8000/employee/create/", employee, config)
      .then((response) => {
        console.log(response.data);
        this.setState({ employeePotential: response.data });
        // this.getEmployeePotential();
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
    this.setState({ changedisplay: false });
  };

  updateEmployee(id) {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };

    const employee = {
      firstname: this.state.employeeParams.firstname,
      lastname: this.state.employeeParams.lastname,
      email: this.state.employeeParams.email,
      designation: this.state.employeeParams.designation,
      department: this.state.employeeParams.department,
      grade: this.state.employeeParams.grade,
      specialization: this.state.employeeParams.specialization,
      type: this.state.employeeParams.type,
      experience: this.state.employeeParams.experience,
      is_html: this.state.isHtml_checked,
      is_react: this.state.isReact_checked,
      is_analytical: this.state.isAnalytical_checked,
      is_teamwork: this.state.isTeamwork_checked,
      is_passion: this.state.isPassion_checked,
      is_css: this.state.isCss_checked,
      is_cplus: this.state.isCplus_checked,
      is_oracle: this.state.isOracle_checked,
      is_csharp: this.state.isChash_checked,
      is_php: this.state.isPhp_checked,
      is_ruby: this.state.isRuby_checked,
      is_java: this.state.isJava_checked,
      is_creativity: this.state.isCreativity_checked,
      is_python: this.state.isPython_checked,
      is_mysql: this.state.isMysql_checked,
      is_leadership: this.state.isLeadership_checked,
      is_angular: this.state.isAngular_checked,
      is_nosql: this.state.isNosql_checked,
      is_communication: this.state.isCommunication_checked,
      is_js: this.state.isJs_checked,
      is_bootstrap: this.state.isBootstrap_checked,
    };

    console.log(employee);
    axios
      .put("http://127.0.0.1:8000/employee/update/" + id + "/", employee, config)
      .then((response) => {
        if (response.data != null) {
          this.setState({ employeePotential: response.data });
          console.log(this.state.employeePotential);
          this.setState({ employeePotentialModalShow: true });
          this.setState({ changedisplay: true });
        }
      });

  }

  render() {
    const { formErrors } = this.state;
    let employeePotentialViewModalClose = () => {
      this.setState({ employeePotentialModalShow: false });
    };
    return (
      <div class="employeeCss">
        {/* <h4>New Staff</h4> */}
        {this.state.buttondisabled ? (
          <h4> </h4>
        ) : (
          <h4>New Staff</h4>
        )}
        <div class="Back">
          <form>
            <div class="grid-containeremp">
              {/*FirstName label*/}
              <div class="item1">
                <label class="lab1" for="firstname">
                  First Name
                </label>
                <br />
                <label class="lab3" for="lastname">
                  Last Name
                </label>
                <br />
                <label class="lab3" for="email">
                  Email
                </label>
                <br />
                <label class="lab3" for="designation">
                  Designation
                </label>
                <br />
                <label class="lab3" for="department">
                  Department
                </label>
                <br />
                <label class="lab3" for="experience">
                  Experience
                </label>
              </div>
              {/*FirstName textfield*/}
              <div class="item2">
                <input
                  type="text"
                  id="firstname"
                  name="firstname"
                  defaultValue={this.props.firstname}
                  onChange={this.changeHandler}
                />

                {formErrors.firstname.length > 0 && (
                  <span className="errorMessage">{formErrors.firstname}</span>
                )}

                <input
                  type="text"
                  id="lastname"
                  name="lastname"
                  defaultValue={this.props.lastname}
                  onChange={this.changeHandler}
                />

                {formErrors.lastname.length > 0 && (
                  <span className="errorMessage">{formErrors.lastname}</span>
                )}

                <input
                  type="text"
                  id="email1"
                  name="email"
                  defaultValue={this.props.email}
                  onChange={this.changeHandler}
                />

                {formErrors.email.length > 0 && (
                  <span className="errorMessage">{formErrors.email}</span>
                )}

                <input
                  type="text"
                  id="designation"
                  name="designation"
                  defaultValue={this.props.designation}
                  onChange={this.changeHandler}
                />

                {formErrors.designation.length > 0 && (
                  <span className="errorMessage">{formErrors.designation}</span>
                )}

                <input
                  type="text"
                  id="department"
                  name="department"
                  defaultValue={this.props.department}
                  onChange={this.changeHandler}
                />

                {formErrors.department.length > 0 && (
                  <span className="errorMessage">{formErrors.department}</span>
                )}
                <br />
                <input
                  type="number"
                  id="experience"
                  name="experience"
                  defaultValue={this.props.experience}
                  onChange={this.changeHandler}
                  min="0"
                />
                <br />
                {formErrors.experience.length > 0 && (
                  <span className="errorMessage">{formErrors.experience}</span>
                )}
                <br />
              </div>

              {/*Skills label*/}
              <div class="item9">
                <label class="lab1" for="skills">
                  Skills
                </label>
              </div>

              <div class="item10">
                <div class="grid-container1">
                  {/*1st row of skills */}
                  <div className="itemc1">
                    <input
                      type="checkbox"
                      id="is_html"
                      name="is_html"
                      checked={this.state.isHtml_checked}
                      defaultValue={this.props.is_html}
                      onChange={this.changeTick}
                    />
                    <label className="clabel" for="is_html">
                      {" "}
                      Html
                    </label>
                    <br />
                    <input
                      type="checkbox"
                      id="is_analytical"
                      name="is_analytical"
                      checked={this.state.isAnalytical_checked}
                      defaultValue={this.props.is_analytical}
                      onChange={this.analyticalChangeHandler}
                    />
                    <label className="clabel" for="is_analytical">
                      {" "}
                      Analytical{" "}
                    </label>
                  </div>
                  <div className="itemc2">
                    <input
                      type="checkbox"
                      id="is_react"
                      name="is_react"
                      checked={this.state.isReact_checked}
                      defaultValue={this.props.is_react}
                      onChange={this.reactChangeHandler}
                    />
                    <label className="clabel" for="is_react">
                      {" "}
                      React
                    </label>
                    <br />
                    <input
                      type="checkbox"
                      id="is_teamwork"
                      name="is_teamwork"
                      checked={this.state.isTeamwork_checked}
                      defaultValue={this.props.is_teamwork}
                      onChange={this.teamworkChangeHandler}
                    />
                    <label className="clabel" for="is_teamwork">
                      {" "}
                      Teamwork{" "}
                    </label>
                  </div>
                  <div className="itemc4">
                    <input
                      type="checkbox"
                      id="is_css"
                      name="is_css"
                      checked={this.state.isCss_checked}
                      defaultValue={this.props.is_css}
                      onChange={this.changeTick}
                    />
                    <label className="clabel" for="is_css">
                      {" "}
                      CSS{" "}
                    </label>
                  </div>
                  <div className="itemc5">
                    <input
                      type="checkbox"
                      id="is_cplus"
                      name="is_cplus"
                      checked={this.state.isCplus_checked}
                      defaultValue={this.props.is_cplus}
                      onChange={this.cplusChangeHandler}
                    />
                    <label className="clabel" for="is_cplus">
                      {" "}
                      C++{" "}
                    </label>
                  </div>

                  <div className="itemc7">
                    <input
                      type="checkbox"
                      id="is_csharp"
                      name="is_csharp"
                      checked={this.state.isChash_checked}
                      defaultValue={this.props.is_csharp}
                      onChange={this.chashChangeHandler}
                    />
                    <label className="clabel" for="is_csharp">
                      {" "}
                      C#
                    </label>
                  </div>

                  {/*2nd row of skills */}
                  <div className="itemc8">
                    <input
                      type="checkbox"
                      id="is_php"
                      name="is_php"
                      checked={this.state.isPhp_checked}
                      defaultValue={this.props.is_php}
                      onChange={this.phpChangeHandler}
                    />
                    <label className="clabel" for="is_php">
                      {" "}
                      Php
                    </label>
                  </div>
                  <div className="itemc3">
                    <input
                      type="checkbox"
                      id="is_ruby"
                      name="is_ruby"
                      checked={this.state.isRuby_checked}
                      defaultValue={this.props.is_ruby}
                      onChange={this.rubyChangeHandler}
                    />
                    <label className="clabel" for="is_ruby">
                      {" "}
                      Ruby
                    </label>
                    <br />
                    <input
                      type="checkbox"
                      id="is_passion"
                      name="is_passion"
                      checked={this.state.isPassion_checked}
                      defaultValue={this.props.is_passion}
                      onChange={this.passionChangeHandler}
                    />
                    <label className="clabel" for="is_passion">
                      {" "}
                      Passion{" "}
                    </label>
                  </div>
                  <div className="itemc10">
                    <input
                      type="checkbox"
                      id="is_java"
                      name="is_java"
                      checked={this.state.isJava_checked}
                      defaultValue={this.props.is_java}
                      onChange={this.javaChangeHandler}
                    />
                    <label className="clabel" for="is_java">
                      {" "}
                      Java
                    </label>
                  </div>
                  <div className="itemc11">
                    <input
                      type="checkbox"
                      id="is_oracle"
                      name="is_oracle"
                      checked={this.state.isOracle_checked}
                      defaultValue={this.props.is_oracle}
                      onChange={this.oracleChangeHandler}
                    />
                    <label className="clabel" for="is_oracle">
                      {" "}
                      Oracle{" "}
                    </label>
                  </div>
                  <div className="itemc6">
                    <input
                      type="checkbox"
                      id="is_creativity"
                      name="is_creativity"
                      checked={this.state.isCreativity_checked}
                      defaultValue={this.props.is_creativity}
                      onChange={this.creativityChangeHandler}
                    />
                    <label className="clabel" for="is_creativity">
                      Creativity
                    </label>
                  </div>
                  <div className="itemc13">
                    <input
                      type="checkbox"
                      id="is_nosql"
                      name="is_nosql"
                      checked={this.state.isNosql_checked}
                      defaultValue={this.props.is_nosql}
                      onChange={this.nosqlChangeHandler}
                    />
                    <label className="clabel" for="is_nosql">
                      {" "}
                      NoSQL
                    </label>
                  </div>
                  <div className="itemc14">
                    <input
                      type="checkbox"
                      id="is_bootstrap"
                      name="is_bootstrap"
                      checked={this.state.isBootstrap_checked}
                      defaultValue={this.props.is_bootstrap}
                      onChange={this.bootstrapChangeHandler}
                    />
                    <label className="clabel" for="is_bootstrap">
                      {" "}
                      Bootstrap
                    </label>
                  </div>

                  {/*3rd row of skills */}
                  <div className="itemc15">
                    <input
                      type="checkbox"
                      id="is_python"
                      name="is_python"
                      checked={this.state.isPython_checked}
                      defaultValue={this.props.is_python}
                      onChange={this.pythonChangeHandler}
                    />
                    <label className="clabel" for="is_python">
                      {" "}
                      Python
                    </label>
                  </div>
                  <div className="itemc16">
                    <input
                      type="checkbox"
                      id="is_mysql"
                      name="is_mysql"
                      checked={this.state.isMysql_checked}
                      defaultValue={this.props.is_mysql}
                      onChange={this.mysqlChangeHandler}
                    />
                    <label className="clabel" for="is_mysql">
                      {" "}
                      MySQL
                    </label>
                  </div>
                  <div className="itemc12">
                    <input
                      type="checkbox"
                      id="is_leadership"
                      name="is_leadership"
                      checked={this.state.isLeadership_checked}
                      defaultValue={this.props.is_leadership}
                      onChange={this.leadershipChangeHandler}
                    />
                    <label className="clabel" for="is_leadership">
                      {" "}
                      Leadership
                    </label>
                  </div>
                  <div className="itemc18">
                    <input
                      type="checkbox"
                      id="is_angular"
                      name="is_angular"
                      checked={this.state.isAngular_checked}
                      defaultValue={this.props.is_angular}
                      onChange={this.angularChangeHandler}
                    />
                    <label class="clabel" for="is_angular">
                      {" "}
                      Angular
                    </label>
                  </div>
                  <div className="itemc19">
                    <input
                      type="checkbox"
                      id="is_js"
                      name="is_js"
                      checked={this.state.isJs_checked}
                      defaultValue={this.props.is_js}
                      onChange={this.changeTick}
                    />
                    <label className="clabel" for="is_js">
                      {" "}
                      Java Script
                    </label>
                  </div>
                  <div className="itemc9">
                    <input
                      type="checkbox"
                      id="is_communication"
                      name="is_communication"
                      checked={this.state.isCommunication_checked}
                      defaultValue={this.props.is_communication}
                      onChange={this.communicationChangeHandler}
                    />
                    <label className="clabel" for="is_communication">
                      {" "}
                      Communication{" "}
                    </label>
                  </div>
                  {/*Predict button*/}
                  <div class="item14">
                    {/* <button class="buttonPredict" onClick={this.employee}>
                      Add
                    </button> */}
                    {this.state.buttondisabled ? (
                      <Button
                        variant="light"
                        type="button"
                        //className="updateButton"
                        onClick={() => this.updateEmployee(this.props.id)}
                      >
                        UPDATE
                      </Button>
                    ) : (
                      <Button
                        variant="light"
                        type="button"
                        //className="buttonPredict"
                        onClick={this.clickHandler}
                      >
                        Add
                      </Button>
                    )}
                  </div>
                </div>
              </div>
            </div>
          </form>
        </div>

        <EmployeePotentialViewModal
          show={this.state.employeePotentialModalShow}
          name={this.state.employeePotential.firstname}
          type={this.state.employeePotential.type}
          grade={this.state.employeePotential.grade}
          specialization={this.state.employeePotential.specialization}
          onHide={employeePotentialViewModalClose}
          updateison={this.state.changedisplay}
        />
      </div>
    );
  }
}
export default AddEmployee;
