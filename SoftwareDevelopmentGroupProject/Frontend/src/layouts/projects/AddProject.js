import { Component } from "react";
import "./project.css";
import { MDBContainer, MDBRow, MDBCol } from "mdbreact";
import "bootstrap-css-only/css/bootstrap.min.css";
import "mdbreact/dist/css/mdb.css";
import axios from "axios";
import UserIcon from "../../assets/images/userIcon.png";
import Popup from "../../components/Popup/Popup";
import { useState } from "react";
import { Button } from 'react-bootstrap';

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

export class AddProject extends Component {
  constructor(props) {
    super(props);
    this.state = {
      employee_checked: false,
      selectedEmployeeArray: [],
      isHtml_checked: this.props.updateison ? this.props.is_html : false,
      isReact_checked: this.props.updateison ? this.props.is_react : false,
      isCss_checked: this.props.updateison ? this.props.is_css : false,
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
      count_checked: 0,
      employeeDetails: [],
      toSuggestEmployees: [],
      neededFrontendSkills: [],
      neededBackendSkills: [],
      neededFullstackSkills: [],
      isEmployeesPicked: false,
      frontend: 0,
      backend: 0,
      fullstack: 0,
      show: false,
      popupModalShow: false,
      selectvalue1: false,
      selectvalue2: false,
      selectvalue3: false,
      selectvalue4: false,
      selectvalue5: false,
      selectvalue6: false,
      buttondisabled: false,
      buttonPopup: false,
      payloadBody: "",
      html_url: "",
      projectParams: {
        client: this.props.updateison ? this.props.client : "",
        address: this.props.updateison ? this.props.address : "",
        email: this.props.updateison ? this.props.email : "",
        contactno: this.props.updateison ? this.props.contactno : "",
        solution_description: this.props.updateison ? this.props.solution_description : "",
        date_admitted: this.props.updateison ? this.props.date_admitted : "",
        delivery_date: this.props.updateison ? this.props.delivery_date : "",
        repolink: this.props.updateison ? this.props.repolink : "",
        priority: this.props.updateison ? this.props.priority : "Low",
        status: this.props.updateison ? this.props.status : "OnGoing",
      },
      formErrors: {
        client: "",
        contactno: "",
        email: "",
        address: "",
        solution_description: "",
        repolink: "",
      },
    };
  }

  componentDidMount() {
    let projectpriority = this.props.priority;
    let projectstatus = this.props.status;

    if (projectpriority === "High") {
      this.setState({ selectvalue1: true });
    } else if (projectpriority === "Medium") {
      this.setState({ selectvalue2: true });
    } else {
      this.setState({ selectvalue3: true });
    }

    if (projectstatus === "Completed") {
      this.setState({ selectvalue4: true });
    } else if (projectstatus === "Pending") {
      this.setState({ selectvalue5: true });
    } else {
      this.setState({ selectvalue6: true });
    }
    if (this.props.updateison) {
      this.setState({ buttondisabled: true });
    }

    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };

    axios.get("http://127.0.0.1:8000/employee/get/", config)
      .then((response) => {
        this.setState({ employeeDetails: response.data });
        console.log(this.state.employeeDetails);
      });
  }

  htmlChangeHandler = (event) => {
    this.setState({ isHtml_checked: !this.state.isHtml_checked });
  };
  reactChangeHandler = (event) => {
    this.setState({ isReact_checked: !this.state.isReact_checked });
  };
  cssChangeHandler = (event) => {
    this.setState({ isCss_checked: !this.state.isCss_checked });
  };
  cplusChangeHandler = (event) => {
    this.setState({ isCplus_checked: !this.state.isCplus_checked });
  };
  oracleChangeHandler = (event) => {
    this.setState({ isOracle_checked: !this.state.isOracle_checked });
  };
  chashChangeHandler = (event) => {
    this.setState({ isChash_checked: !this.state.isChash_checked });
  };
  phpChangeHandler = (event) => {
    this.setState({ isPhp_checked: !this.state.isPhp_checked });
  };
  rubyChangeHandler = (event) => {
    this.setState({ isRuby_checked: !this.state.isRuby_checked });
  };
  javaChangeHandler = (event) => {
    this.setState({ isJava_checked: !this.state.isJava_checked });
  };
  pythonChangeHandler = (event) => {
    this.setState({ isPython_checked: !this.state.isPython_checked });
  };
  mysqlChangeHandler = (event) => {
    this.setState({ isMysql_checked: !this.state.isMysql_checked });
  };
  angularChangeHandler = (event) => {
    this.setState({ isAngular_checked: !this.state.isAngular_checked });
  };
  nosqlChangeHandler = (event) => {
    this.setState({ isNosql_checked: !this.state.isNosql_checked });
  };
  jsChangeHandler = (event) => {
    this.setState({ isJs_checked: !this.state.isJs_checked });
  };
  bootstrapChangeHandler = (event) => {
    this.setState({ isBootstrap_checked: !this.state.isBootstrap_checked });
  };

  changeHandler = (event) => {
    const { name, value } = event.target;
    let formErrors = this.state.formErrors;

    switch (name) {
      case "client":
        if (value.length === 0) {
          formErrors.client =
            value.length === 0 ? "Cannot leave this field empty" : "";
        } else {
          formErrors.client =
            value.length < 3 ? "minimum 3 characters required" : "";
        }
        break;

      case "contactno":
        if (value.length === 0) {
          formErrors.contactno =
            value.length === 0 ? "Cannot leave this field empty" : "";
        } else {
          formErrors.contactno =
            value.length < 10 ? "contact number should be in 10 digits" : "";
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

      case "address":
        if (value.length === 0) {
          formErrors.address =
            value.length === 0 ? "Cannot leave this field empty" : "";
        } else {
          formErrors.address =
            value.length < 3 ? "minimum 3 characters required" : "";
        }
        break;

      case "solution_description":
        formErrors.solution_description =
          value.length === 0 ? "Cannot leave this field empty" : "";
        break;

      case "repolink":
        formErrors.repolink =
          value.length === 0 ? "Cannot leave this field empty" : "";
        break;
      default:
        break;
    }
    let projectParamsNew = { ...this.state.projectParams };
    let val = event.target.value;

    projectParamsNew[event.target.name] = val;
    this.setState({
      projectParams: projectParamsNew,
    });
  };

  clickHandler = (event) => {
    this.state.count_checked = 0;

    if (this.state.isHtml_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
      this.state.neededFullstackSkills.push("html");
      this.state.neededFrontendSkills.push("html");
    }
    if (this.state.isReact_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
      this.state.neededFullstackSkills.push("react");
      this.state.neededFrontendSkills.push("react");
    }
    if (this.state.isCss_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
      this.state.neededFullstackSkills.push("css");
      this.state.neededFrontendSkills.push("css");
    }
    if (this.state.isCplus_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
      this.state.neededFullstackSkills.push("cplus");
      this.state.neededBackendSkills.push("cplus");
    }
    if (this.state.isOracle_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
      this.state.neededFullstackSkills.push("oracle");
      this.state.neededBackendSkills.push("oracle");
    }
    if (this.state.isChash_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
      this.state.neededFullstackSkills.push("csharp");
      this.state.neededBackendSkills.push("csharp");
    }
    if (this.state.isPhp_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
      this.state.neededFullstackSkills.push("php");
      this.state.neededBackendSkills.push("php");
    }
    if (this.state.isRuby_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
      this.state.neededFullstackSkills.push("ruby");
      this.state.neededBackendSkills.push("ruby");
    }
    if (this.state.isJava_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
      this.state.neededFullstackSkills.push("java");
      this.state.neededBackendSkills.push("java");
    }
    if (this.state.isPython_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
      this.state.neededFullstackSkills.push("python");
      this.state.neededBackendSkills.push("python");
    }
    if (this.state.isMysql_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
      this.state.neededFullstackSkills.push("mysql");
      this.state.neededBackendSkills.push("mysql");
    }
    if (this.state.isAngular_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
      this.state.neededFullstackSkills.push("angular");
      this.state.neededFrontendSkills.push("angular");
    }
    if (this.state.isNosql_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
      this.state.neededFullstackSkills.push("nosql");
      this.state.neededBackendSkills.push("nosql");
    }
    if (this.state.isJs_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
      this.state.neededFullstackSkills.push("js");
      this.state.neededFrontendSkills.push("js");
    }
    if (this.state.isBootstrap_checked) {
      this.setState({ count_checked: this.state.count_checked++ });
      this.state.neededFullstackSkills.push("bootstrap");
      this.state.neededFrontendSkills.push("bootstrap");
    }

    event.preventDefault();
    console.log(this.state.count_checked);

    const user = {
      client: this.state.projectParams.client,
      address: this.state.projectParams.address,
      contactno: this.state.projectParams.contactno,
      date_admitted: this.state.projectParams.date_admitted,
      delivery_date: this.state.projectParams.delivery_date,
      email: this.state.projectParams.email,
      priority: this.state.projectParams.priority,
      status: this.state.projectParams.status,
      is_html: this.state.projectParams.is_html,
      is_react: this.state.projectParams.is_react,
      is_nodejs: this.state.projectParams.is_nodejs,
      is_css: this.state.projectParams.is_css,
      is_cplus: this.state.projectParams.is_cplus,
      is_oracle: this.state.projectParams.is_oracle,
      is_csharp: this.state.projectParams.is_csharp,
      is_php: this.state.projectParams.is_php,
      is_ruby: this.state.projectParams.is_ruby,
      is_java: this.state.projectParams.is_java,
      is_ml: this.state.projectParams.is_ml,
      is_python: this.state.projectParams.is_python,
      is_mysql: this.state.projectParams.is_mysql,
      is_angular: this.state.projectParams.is_angular,
      is_nosql: this.state.projectParams.is_nosql,
      is_js: this.state.projectParams.is_js,
      is_bootstrap: this.state.projectParams.is_bootstrap,
      repolink: this.state.projectParams.repolink,
      solution_description: this.state.projectParams.solution_description,
    };

    if (formValid(this.state.formErrors)) {
      if (
        user.client === "" ||
        user.email === "" ||
        user.address === "" ||
        user.contactno === "" ||
        user.solution_description === "" ||
        user.date_admitted === "" ||
        user.delivery_date === "" ||
        user.repolink === ""
      ) {
        alert("Fill out all fields!");
      } else if (this.state.count_checked < 3) {
        alert("Minimum three skills needed.");
      } else {
        this.setState({ show: true });
      }
    } else {
      alert("Form Invalid");
    }
  };

  project = (event) => {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };
    for (let i = 1; i < this.state.selectedEmployeeArray.length; i++) {


      console.log(this.state.selectedEmployeeArray[i]);
      this.state.employeeDetails.forEach((employee) => {
        // console.log(employee.id);
        // console.log(employee);
        var employee_int = parseInt(this.state.selectedEmployeeArray[i], 10);
        if (employee.id === employee_int) {
          console.log(employee);
          employee.projects = this.state.projectParams.solution_description;
          axios.put("http://127.0.0.1:8000/employee/assign/project/" + employee.id + "/", employee, config)
            .then((response) => {
              if (response.data != null) {
                console.log(this.props.employeedata);
                console.log(response.data);
              }
            });
        }
      });

    }
    const project = {
      client: this.state.projectParams.client,
      address: this.state.projectParams.address,
      contactno: this.state.projectParams.contactno,
      date_admitted: this.state.projectParams.date_admitted,
      delivery_date: this.state.projectParams.delivery_date,
      email: this.state.projectParams.email,
      priority: this.state.projectParams.priority,
      repolink: this.state.projectParams.repolink,
      solution_description: this.state.projectParams.solution_description,
      status: this.state.projectParams.status,
      is_html: this.state.isHtml_checked,
      is_react: this.state.isReact_checked,
      is_css: this.state.isCss_checked,
      is_cplus: this.state.isCplus_checked,
      is_oracle: this.state.isOracle_checked,
      is_csharp: this.state.isChash_checked,
      is_php: this.state.isPhp_checked,
      is_ruby: this.state.isRuby_checked,
      is_java: this.state.isJava_checked,
      is_python: this.state.isPython_checked,
      is_mysql: this.state.isMysql_checked,
      is_angular: this.state.isAngular_checked,
      is_nosql: this.state.isNosql_checked,
      is_js: this.state.isJs_checked,
      is_bootstrap: this.state.isBootstrap_checked,
    };
    console.log(project);
    axios
      .post("http://127.0.0.1:8000/project/create/", project, config)
      .then((response) => {
        console.log(response.data);
        console.log("abs");
        alert("Project added successfully!");
        setTimeout(() => {
          this.setState({ loading: false });
        }, 4000);
      })
      .catch((error) => {
        alert("Failed to add this project!");
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
    setTimeout(() => {
      this.setState({ show: false });
    }, 4000);
  };

  updateProject(id) {
    const token = localStorage.getItem("token");
    const config = {
      headers: { Authorization: `Token ${token}` },
    };

    const project = {
      client: this.state.projectParams.client,
      address: this.state.projectParams.address,
      contactno: this.state.projectParams.contactno,
      date_admitted: this.state.projectParams.date_admitted,
      delivery_date: this.state.projectParams.delivery_date,
      email: this.state.projectParams.email,
      priority: this.state.projectParams.priority,
      repolink: this.state.projectParams.repolink,
      solution_description: this.state.projectParams.solution_description,
      status: this.state.projectParams.status,
      is_html: this.state.isHtml_checked,
      is_react: this.state.isReact_checked,
      is_css: this.state.isCss_checked,
      is_cplus: this.state.isCplus_checked,
      is_oracle: this.state.isOracle_checked,
      is_csharp: this.state.isChash_checked,
      is_php: this.state.isPhp_checked,
      is_ruby: this.state.isRuby_checked,
      is_java: this.state.isJava_checked,
      is_python: this.state.isPython_checked,
      is_mysql: this.state.isMysql_checked,
      is_angular: this.state.isAngular_checked,
      is_nosql: this.state.isNosql_checked,
      is_js: this.state.isJs_checked,
      is_bootstrap: this.state.isBootstrap_checked,
    };

    axios
      .put("http://127.0.0.1:8000/project/update/" + id + "/", project, config)
      .then((response) => {
        if (response.data != null) {
          console.log(response.data);
        }
      });
  }
  selectEmployee = (event) => {
    const target = event.target;
    var value = target.value;

    if (target.checked) {
      this.state.selectedEmployeeArray[value] = value;
    } else {
      this.state.selectedEmployeeArray.splice(value, 1);
    }
  }
  getEmployeeSkills(employee) {
    var employeeSkills = [];

    if (employee.type === "Full-Stack") {
      if (employee.is_angular === true) employeeSkills.push("angular");
      if (employee.is_react === true) employeeSkills.push("react");
      if (employee.is_bootstrap === true) employeeSkills.push("bootstrap");
      if (employee.is_html === true) employeeSkills.push("html");
      if (employee.is_js === true) employeeSkills.push("js");
      if (employee.is_css === true) employeeSkills.push("css");
      if (employee.is_java === true) employeeSkills.push("java");
      if (employee.is_python === true) employeeSkills.push("python");
      if (employee.is_csharp === true) employeeSkills.push("charp");
      if (employee.is_cplus === true) employeeSkills.push("cplus");
      if (employee.is_php === true) employeeSkills.push("php");
      if (employee.is_ruby === true) employeeSkills.push("ruby");
      if (employee.is_mysql === true) employeeSkills.push("mysql");
      if (employee.is_nosql === true) employeeSkills.push("nosql");
      if (employee.is_oracle === true) employeeSkills.push("oracle");
    }
    else if (employee.type === "Frontend") {
      if (employee.is_html === true) employeeSkills.push("html");
      if (employee.is_js === true) employeeSkills.push("js");
      if (employee.is_angular === true) employeeSkills.push("angular");
      if (employee.is_react === true) employeeSkills.push("react");
      if (employee.is_bootstrap === true) employeeSkills.push("bootstrap");
      if (employee.is_css === true) employeeSkills.push("css");
    }
    else {
      if (employee.is_java === true) employeeSkills.push("java");
      if (employee.is_python === true) employeeSkills.push("python");
      if (employee.is_csharp === true) employeeSkills.push("charp");
      if (employee.is_cplus === true) employeeSkills.push("cplus");
      if (employee.is_php === true) employeeSkills.push("php");
      if (employee.is_ruby === true) employeeSkills.push("ruby");
      if (employee.is_mysql === true) employeeSkills.push("mysql");
      if (employee.is_nosql === true) employeeSkills.push("nosql");
      if (employee.is_oracle === true) employeeSkills.push("oracle");
    }

    return employeeSkills;
  }

  searchEmployeeFirstStrive(devCount, devType) {
    var count = devCount;
    var notSelectedEmployees = [];

    this.state.employeeDetails.forEach((employee) => {
      var found = false;
      this.state.toSuggestEmployees.forEach((selectedEmployee) => {
        if (employee.id === selectedEmployee.id) {
          found = true;
        }
      });
      if (!found) {
        notSelectedEmployees.push(employee);
      }
      // if(found){
      //   for(let i=0;i<notSelectedEmployees.length;i++){
      //     if(employee.id===notSelectedEmployees.)
      //   }
      // }
    });
    notSelectedEmployees.forEach((employee) => {

      var employeeSkills = this.getEmployeeSkills(employee);
      var neededSkills = [];

      if (devType === "Frontend") neededSkills = this.state.neededFrontendSkills;
      else if (devType === "Backend") neededSkills = this.state.neededBackendSkills;
      else neededSkills = this.state.neededFullstackSkills;

      var skillCount = 0;
      neededSkills.forEach((neededSkill) => {
        employeeSkills.forEach((employeeSkill) => {
          if (neededSkill === employeeSkill) {
            skillCount += 1;
          }
        });
      });

      if (count > 0 && employee.projects === null) {
        if (this.state.projectParams.priority === "High") {
          if ((employee.type === devType && employee.grade === "Level-3" && skillCount === neededSkills.length) || employee.specialization === devType) {
            this.state.toSuggestEmployees.push(employee);
            count -= 1;
          }
        }
        else if (this.state.projectParams.priority === "Medium") {
          if (employee.type === devType && employee.grade === "Level-2" && skillCount === neededSkills.length && employee.specialization === "None") {
            this.state.toSuggestEmployees.push(employee);
            count -= 1;
          }
        }
        else {
          if (employee.type === devType && employee.grade === "Level-1" && skillCount === neededSkills.length && employee.specialization === "None") {
            this.state.toSuggestEmployees.push(employee);
            count -= 1;
          }
        }
      }
    });

    if (count !== 0) this.searchEmployeeSecondStrive(count, devType);
  }

  searchEmployeeSecondStrive(devCount, devType) {
    var count = devCount;
    var notSelectedEmployees = [];

    notSelectedEmployees.forEach((employee) => {

      var employeeSkills = this.getEmployeeSkills(employee);
      var neededSkills = [];

      if (devType === "Frontend") neededSkills = this.state.neededFrontendSkills;
      else if (devType === "Backend") neededSkills = this.state.neededBackendSkills;
      else neededSkills = this.state.neededFullstackSkills;

      var skillCount = 0;
      neededSkills.forEach((neededSkill) => {
        employeeSkills.forEach((employeeSkill) => {
          if (neededSkill === employeeSkill) {
            skillCount += 1;
          }
        });
      });

      if (count > 0 && employee.projects === null) {
        if (this.state.projectParams.priority === "High") {
          if ((employee.type === devType && employee.grade === "Level-3" && skillCount > 0) || employee.specialization === devType) {
            this.state.toSuggestEmployees.push(employee);
            count -= 1;
          }
        }
        else if (this.state.projectParams.priority === "Medium") {
          if (employee.type === devType && employee.grade === "Level-2" && skillCount > 0 && employee.specialization === "None") {
            this.state.toSuggestEmployees.push(employee);
            count -= 1;
          }
        }
        else {
          if (employee.type === devType && employee.grade === "Level-1" && skillCount > 0 && employee.specialization === "None") {
            this.state.toSuggestEmployees.push(employee);
            count -= 1;
          }
        }
      }


    });

    if (count !== 0) this.searchEmployeeThirdStrive(count, devType);
  }

  searchEmployeeThirdStrive(devCount, devType) {
    var count = devCount;
    var notSelectedEmployees = [];

    this.state.employeeDetails.forEach((employee) => {
      var found = false;
      this.state.toSuggestEmployees.forEach((selectedEmployee) => {
        if (employee.id === selectedEmployee.id) {
          found = true;
        }
      });
      if (!found) {
        notSelectedEmployees.push(employee);
      }
    });


    notSelectedEmployees.forEach((employee) => {
      if (count > 0 && employee.projects === null) {
        if (this.state.projectParams.priority === "High") {
          if ((employee.type === devType && employee.grade === "Level-3") || employee.specialization === devType) {
            this.state.toSuggestEmployees.push(employee);
            count -= 1;
          }
        }
        else if (this.state.projectParams.priority === "Medium") {
          if (employee.type === devType && employee.grade === "Level-2" && employee.specialization === "None") {
            this.state.toSuggestEmployees.push(employee);
            count -= 1;
          }
        }
        else {
          if (employee.type === devType && employee.grade === "Level-1" && employee.specialization === "None") {
            this.state.toSuggestEmployees.push(employee);
            count -= 1;
          }
        }
      }
    });
  }

  provideSuggestedEmployees = () => {
    this.setState({ isEmployeesPicked: true });

    this.searchEmployeeFirstStrive(this.state.frontend, "Frontend");
    this.searchEmployeeFirstStrive(this.state.backend, "Backend");
    this.searchEmployeeFirstStrive(this.state.fullstack, "Full-Stack");
  }

  render() {
    let popupModalClose = () =>
      this.setState({ popupModalShow: false });

    const CreateRepo = () => {
      return (
        <div className="item13">
          <button
            type="button"
            className="addbutton"
            onClick={() => this.setState({ popupModalShow: true })}>
            +
            </button>
          <label id="createRepo" class="createRepo">
            CREATE NEW REPOSITORY
          </label>

          <Popup
            show={this.state.popupModalShow}
            onHide={popupModalClose}>
            <CreateRepoForm />
          </Popup>


        </div>
      );
    }


    const CreateRepoForm = (props) => {
      const [payloadBody, setpayloadBody] = useState("");

      const CreateGit = () => {
        const API_URL = "https://api.github.com";
        const payload = { name: `${payloadBody}`, private: false };

        const GITHUB_TOKEN = "0ee2e0cd74c44860f411f5bccc34a6ba0e1e6754";
        const config = {
          headers: {
            Authorization: `token ${GITHUB_TOKEN}`,
            Accept: `application/vnd.github.v3+json`,
          },
        };

        axios
          .post(API_URL + "/user/repos", payload, config)
          .then((response) => {
            console.log(response.data);
            console.log(response.data.html_url);
            navigator.clipboard.writeText(response.data.html_url);
            props.setTriger(false);
          })
          .catch((error) => {
            if (error.response) {
              // Request made and server responded
              console.log("response_data : " + error.response.data);
              console.log("response_status : " + error.response.status);
              console.log("response_headers : " + error.response.headers);

              if (error.response.status === 400) {
                console.log("Invalid credientials");
              }
            } else if (error.request) {
              console.log("server is offline");
            } else {
              // Something happened in setting up the request that triggered an Error
              console.log("Something went wrong. Please try again later.");
            }
          });
      };

      return (
        <MDBContainer>
          <MDBRow>
            <MDBCol md="6">
              <form>
                <p className="h4 text-center mb-4" style={{ margin: "0 auto", width: "420px" }}>Create New Repo</p>
                <div style={{ margin: "0 auto", width: "420px" }}>
                  <label htmlFor="defaultFormLoginEmailEx" className="grey-text">
                    Repo Name
                </label>
                  <input
                    type="text"
                    id="defaultFormLoginEmailEx"
                    className="form-control"
                    onChange={(e) => setpayloadBody(e.target.value)}
                  />
                  {formErrors.repolink.length > 0 && (
                    <span className="errorMessage">{formErrors.repolink}</span>
                  )}

                  <div className="text-center mt-4" style={{ margin: "0 auto", width: "420px" }}>
                    <Button
                      type="button"
                      className="createrepo-button"
                      onClick={() => CreateGit()}
                    >
                      Create
                </Button>
                  </div>
                </div>

                {console.log(payloadBody)}

              </form>
            </MDBCol>
          </MDBRow>
        </MDBContainer>
      );
    };
    let date_admitted = this.props.date_admitted;
    if (date_admitted != null) {
      date_admitted = date_admitted.substring(0, date_admitted.length - 1);
    }
    let delivery_date = this.props.delivery_date;
    if (delivery_date != null) {
      delivery_date = delivery_date.substring(0, delivery_date.length - 1);
    }
    const { formErrors } = this.state;



    var renderSuggestedEmployees = this.state.toSuggestEmployees.map((employee) => (
      <div className="col" key={employee.id} >
        <div class="card h-100" >


          <div class="card-header"><input type="checkbox" value={employee.id}
            onChange={this.selectEmployee} />
            <label class="form-check-label" for="exampleCheck1">_Select Employee</label></div>


          <img className="userImage" alt="img" src={UserIcon} />
          <div className="card-body">
            <h5 className="card-title">{employee.firstname}</h5>
            <p className="card-text">Email: {employee.email}</p>
            <p className="card-text">Type: {employee.type}</p>
            <p className="card-text">Grade: {employee.grade}</p>
            <p className="card-text">Specialization: {employee.specialization}</p>
          </div>
        </div>
      </div>
    ));

    return (
      <div className="addprojectCss">
        {/* <h4>New Project</h4> */}
        {this.state.buttondisabled ? (
          <h4> </h4>
        ) : (
          <h4>New Project</h4>
        )}
        {this.state.show === false ? (
          <div className="bluebackground1">
            <form>
              <div className="grid-container">
                {/*Client label*/}
                <div className="item1">
                  <label className="lab2" htmlFor="client">
                    Client
                  </label>
                  <br />
                  <label className="lab2" htmlFor="address">
                    Address
                  </label>
                  <br />
                  <label className="lab2" htmlFor="email">
                    Email
                  </label>
                  <br />
                  <label className="lab2" htmlFor="contactno">
                    Contact Number
                  </label>
                  <br />
                  <label className="lab2" htmlFor="solution">
                    Solution
                  </label>
                  <br />
                  <label className="lab1" htmlFor="status">
                    Status
                  </label>
                </div>
                {/*Client textfield*/}
                <div className="item2">
                  <input
                    placeholder="client name"
                    type="text"
                    id="client"
                    name="client"
                    defaultValue={this.props.client}
                    onChange={this.changeHandler}
                  />
                  <br />
                  {formErrors.client.length > 0 && (
                    <span className="errorMessage">{formErrors.client}</span>
                  )}
                  <br />
                  <input
                    placeholder="client address"
                    type="text"
                    id="address"
                    name="address"
                    defaultValue={this.props.address}
                    onChange={this.changeHandler}
                  />
                  <br />
                  {formErrors.address.length > 0 && (
                    <span className="errorMessage">{formErrors.address}</span>
                  )}
                  <br />
                  <input
                    placeholder="client email"
                    type="text"
                    id="email"
                    name="email"
                    defaultValue={this.props.email}
                    onChange={this.changeHandler}
                  />
                  <br />
                  {formErrors.email.length > 0 && (
                    <span className="errorMessage">{formErrors.email}</span>
                  )}
                  <br />
                  <input
                    placeholder="client contact number"
                    type="text"
                    id="contactno"
                    name="contactno"
                    defaultValue={this.props.contactno}
                    onChange={this.changeHandler}
                  />
                  <br />
                  {formErrors.contactno.length > 0 && (
                    <span className="errorMessage">{formErrors.contactno}</span>
                  )}
                  <br />
                  <input
                    placeholder="solution description"
                    type="text"
                    id="solution_description"
                    name="solution_description"
                    defaultValue={this.props.solution_description}
                    required
                    onChange={this.changeHandler}
                  />
                  <br />
                  {formErrors.solution_description.length > 0 && (
                    <span className="errorMessage">
                      {formErrors.solution_description}
                    </span>
                  )}
                  <br />
                  <br />
                  <div className="item8">
                    <select
                      onChange={this.changeHandler}
                      id="status"
                      name="status"
                    >
                      <option selected={this.state.selectvalue4}>Completed</option>
                      <option selected={this.state.selectvalue5}>Pending</option>
                      <option selected={this.state.selectvalue6}>OnGoing</option>
                    </select>
                  </div>
                </div>

                {/*Due date label*/}
                <div className="item5">
                  <label className="lab2" for="date_admitted">
                    Date Admitted
                  </label>
                  <br />
                  <label id="dueDate" for="date">
                    Due Date
                  </label>
                </div>

                {/*Due date field*/}
                <div className="item6">
                  <input
                    type="datetime-local"
                    id="date_admitted"
                    name="date_admitted"
                    defaultValue={date_admitted}
                    onChange={this.changeHandler}
                  />
                  <br />
                  <input
                    type="datetime-local"
                    id="delivery_date"
                    name="delivery_date"
                    defaultValue={delivery_date}
                    onChange={this.changeHandler}
                  />
                </div>

                {/*Priority label*/}
                <div className="item7">
                  <label id="priorityLabel" className="labl" htmlFor="Priority">
                    Priority
                  </label>
                </div>

                {/*Priority selection field*/}
                <div className="item8">
                  <select
                    onChange={this.changeHandler}
                    id="priority"
                    name="priority"
                  >
                    <option selected={this.state.selectvalue1}>High</option>
                    <option selected={this.state.selectvalue2}>Medium</option>
                    <option selected={this.state.selectvalue3}>Low</option>
                  </select>
                </div>

                {/*Repo link label*/}
                <div className="item11">
                  <label className="lab1" htmlFor="Repo">
                    Repo Link
                  </label>
                </div>

                {/*Repo link textfield*/}
                <div className="item12">
                  <input
                    placeholder="repository link"
                    type="text"
                    id="repolink"
                    name="repolink"
                    defaultValue={this.props.repolink}
                    onChange={this.changeHandler}
                  />
                  {/*plus button and create new repo label*/}
                  <CreateRepo />
                  {formErrors.repolink.length > 0 && (
                    <span className="errorMessage">{formErrors.repolink}</span>
                  )}
                  <br />
                </div>

                {/*Skills label*/}
                <div className="item9">
                  <label className="lab1" for="skills">
                    Skills
                  </label>
                </div>

                <div className="item10">
                  <div className="grid-containerg1">
                    {/*1st row of skills */}
                    <div className="itemc1">
                      <input
                        type="checkbox"
                        id="is_html"
                        name="is_html"
                        defaultChecked={this.props.is_html}
                        onChange={this.htmlChangeHandler}
                      />
                      <label className="clabel" htmlFor="is_html">
                        {" "}
                        Html{" "}
                      </label>
                      <br />

                    </div>
                    <div className="itemc2">
                      <input
                        type="checkbox"
                        id="is_react"
                        name="is_react"
                        defaultChecked={this.props.is_react}
                        onChange={this.reactChangeHandler}
                      />
                      <label className="clabel" htmlFor="is_react">
                        {" "}
                        React
                      </label>
                      <br />

                    </div>
                    <div className="itemc4">
                      <input
                        type="checkbox"
                        id="is_css"
                        name="is_css"
                        defaultChecked={this.props.is_css}
                        onChange={this.cssChangeHandler}
                      />
                      <label className="clabel" htmlFor="is_css">
                        {" "}
                        CSS{" "}
                      </label>
                    </div>
                    <div className="itemc5">
                      <input
                        type="checkbox"
                        id="is_cplus"
                        name="is_cplus"
                        defaultChecked={this.props.is_cplus}
                        onChange={this.cplusChangeHandler}
                      />
                      <label className="clabel" htmlFor="is_cplus">
                        {" "}
                        C++{" "}
                      </label>
                    </div>
                    <div className="itemc11">
                      <input
                        type="checkbox"
                        id="is_oracle"
                        name="is_oracle"
                        defaultChecked={this.props.is_oracle}
                        onChange={this.oracleChangeHandler}
                      />
                      <label className="clabel" htmlFor="is_oracle">
                        {" "}
                        Oracle{" "}
                      </label>
                    </div>
                    <div className="itemc7">
                      <input
                        type="checkbox"
                        id="is_csharp"
                        name="is_csharp"
                        defaultChecked={this.props.is_csharp}
                        onChange={this.chashChangeHandler}
                      />
                      <label className="clabel" htmlFor="is_csharp">
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
                        defaultChecked={this.props.is_php}
                        onChange={this.phpChangeHandler}
                      />
                      <label className="clabel" htmlFor="is_php">
                        {" "}
                        Php
                      </label>
                    </div>
                    <div>
                      <input
                        type="checkbox"
                        id="is_ruby"
                        name="is_ruby"
                        defaultChecked={this.props.is_ruby}
                        onChange={this.rubyChangeHandler}
                      />
                      <label className="clabel" htmlFor="is_ruby">
                        {" "}
                        Ruby
                      </label>
                    </div>
                    <div className="itemc10">
                      <input
                        type="checkbox"
                        id="is_java"
                        name="is_java"
                        defaultChecked={this.props.is_java}
                        onChange={this.javaChangeHandler}
                      />
                      <label className="clabel" htmlFor="is_java">
                        {" "}
                        Java
                      </label>
                    </div>
                    <div className="itemc13">
                      <input
                        type="checkbox"
                        id="is_nosql"
                        name="is_nosql"
                        defaultChecked={this.props.is_nosql}
                        onChange={this.nosqlChangeHandler}
                      />
                      <label className="clabel" htmlFor="is_nosql">
                        {" "}
                        NoSQL
                      </label>
                    </div>
                    <div className="itemc14">
                      <input
                        type="checkbox"
                        id="is_bootstrap"
                        name="is_bootstrap"
                        defaultChecked={this.props.is_bootstrap}
                        onChange={this.bootstrapChangeHandler}
                      />
                      <label className="clabel" htmlFor="is_bootstrap">
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
                        defaultChecked={this.props.is_python}
                        onChange={this.pythonChangeHandler}
                      />
                      <label className="clabel" htmlFor="is_python">
                        {" "}
                        Python
                      </label>
                    </div>
                    <div className="itemc16">
                      <input
                        type="checkbox"
                        id="is_mysql"
                        name="is_mysql"
                        defaultChecked={this.props.is_mysql}
                        onChange={this.mysqlChangeHandler}
                      />
                      <label className="clabel" htmlFor="is_mysql">
                        {" "}
                        MySQL
                      </label>
                    </div>

                    <div className="itemc18">
                      <input
                        type="checkbox"
                        id="is_angular"
                        name="is_angular"
                        defaultChecked={this.props.is_angular}
                        onChange={this.angularChangeHandler}
                      />
                      <label class="clabel" htmlFor="is_angular">
                        {" "}
                        Angular
                      </label>
                    </div>
                    <div className="itemc19">
                      <input
                        type="checkbox"
                        id="is_js"
                        name="is_js"
                        defaultChecked={this.props.is_js}
                        onChange={this.jsChangeHandler}
                      />
                      <label className="clabel" htmlFor="is_js">
                        {" "}
                        Java Script
                      </label>
                    </div>
                    <div className="itemc20">

                    </div>

                    <div className="itemi14">
                      {this.state.buttondisabled ? (
                        <Button
                          variant="light"
                          type="button"
                          //className="updateButton"
                          onClick={() => this.updateProject(this.props.id)}
                        >
                          UPDATE
                        </Button>
                      ) : (
                        <Button
                          variant="light"
                          type="button"
                          //className="buttonFind"
                          onClick={this.clickHandler}
                        >
                          Find
                        </Button>
                      )}
                    </div>
                  </div>
                </div>
              </div>
            </form>
          </div>
        ) : (
          <div className="Back">
            <span className="blockquote text-center">
              Pick Employees to Project
            </span>




            <div className="projectFooter">


              <div class="row">
                <div class="col">
                  <div class="form-group">
                    <label for="inputGroupSelect02">
                      Frontend
                      </label>
                    <div>
                      <input
                        type="number"
                        name="frontend"
                        min="0"
                        value={this.state.frontend}
                        onChange={(e) => this.setState({ frontend: e.target.value })}
                      />
                    </div>
                  </div>
                </div>
                <div class="col">
                  <div class="form-group">
                    <label for="inputGroupSelect02">
                      Backend
                        </label>
                    <div>
                      <input
                        type="number"
                        name="backend"
                        min="0"
                        value={this.state.backend}
                        onChange={(e) => this.setState({ backend: e.target.value })}
                      />
                    </div>
                  </div></div>
                <div class="col">
                  <div class="form-group">
                    <label for="inputGroupSelect02">
                      Fullstack
                      </label>
                    <div>
                      <input
                        type="number"
                        name="fullstack"
                        min="0"
                        value={this.state.fullstack}
                        onChange={(e) => this.setState({ fullstack: e.target.value })}
                      />
                    </div>
                  </div></div>
                <div class="col"><button type="button" class="btn btn-dark"
                  onClick={this.provideSuggestedEmployees}
                >
                  Get Employees
              </button></div>
              </div>
            </div>

            {
              this.state.isEmployeesPicked ?
                <div className="row row-cols-1 row-cols-md-4 g-3">
                  {renderSuggestedEmployees}
                </div> :
                <p></p>
            }
            <div class="row">
              <div class="col-sm-6">
                <Button
                  variant="light"
                  type="button"
                  class="btn btn-light"
                  onClick={this.project}
                >
                  Pick And Save Project
                  </Button>
              </div>
            </div>
          </div >
        )
        }
      </div>
    );
  }
}
export default AddProject;
