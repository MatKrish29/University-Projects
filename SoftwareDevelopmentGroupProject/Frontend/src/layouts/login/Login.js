import React from 'react';
import { MDBRow, MDBCol, MDBInput, MDBBtn, MDBCard, MDBCardBody } from 'mdbreact';
import { Redirect } from "react-router-dom";
import axios from "axios";

import Background from '../../assets/images/bg.svg';

import '@fortawesome/fontawesome-free/css/all.min.css';
import 'bootstrap-css-only/css/bootstrap.min.css';
import 'mdbreact/dist/css/mdb.css';


class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      islogged: false,
      loginParams: { username: "", password: "" },
      error: null,
      loading: false,
    };
  }

  componentDidUpdate(prevProps, prevState) {
    if (prevState.loading !== this.state.loading) {
      setTimeout(() => {
        this.setState({ loading: false });
      }, 3000)
    }
  }


  handleFormChange = event => {
    let loginParamsNew = { ...this.state.loginParams };
    let val = event.target.value;

    loginParamsNew[event.target.name] = val;
    this.setState({
      loginParams: loginParamsNew
    });
  };


  onKeyUp = (event) => {

    if (event.charCode === 13) {
      this.login();
      event.preventDefault();
    }
  }


  login = async (event) => {
    this.setState({ loading: true });
    let username = this.state.loginParams.username;
    let password = this.state.loginParams.password;

    axios.post('http://127.0.0.1:8000/auth/', { username: username, password: password }).then(response => {
      console.log(response.data)
      localStorage.setItem("token", response.data.token);

      this.setState({
        islogged: true,
        loading: true,
      });

      setTimeout(() => {
        this.setState({ loading: false })
      }, 4000)


    }).catch(error => {

      if (error.response) {
        // Request made and server responded
        console.log('response_data : ' + error.response.data);
        console.log('response_status : ' + error.response.status);
        console.log('response_headers : ' + error.response.headers);

        if (error.response.status === 400) {
          this.setState({ error: "Invalid credientials" })
        }

      } else if (error.request) {
        // The request was made but no response was received
        this.setState({ error: "Server is inactive" });
      } else {
        // Something happened in setting up the request that triggered an Error
        this.setState({ error: "Something went wrong. Please try again later." });
      }
      this.setState({ loading: false });
    });

  };


  render() {
    if (localStorage.getItem("token")) {
      return <Redirect to="/" />;
    }

    const aligncenter = {
      margin: 0,
      position: 'absolute',
      top: '50%',
      left: '50%',
      mstransform: 'translate(-50%, -50%)',
      transform: 'translate(-50%, -50%)',
    }

    const loginWrapper = {
      backgroundImage: `url(${Background})`,
      backgroundRepeat: 'no-repeat',
      backgroundSize: 'cover',
      backgroundPosition: 'center',
      width: '100vw',
      height: '100vh',
    }


    return (
      <div className='login-wrapper' style={loginWrapper}>

        <MDBRow className='n'>
          <MDBCol md="9" lg="7" xl="5" className="mx-auto mt-3" style={aligncenter}>
            <MDBCard style={{ borderRadius: '10px' }}>
              <MDBCardBody className="mx-4">
                <div className="text-center">
                  <i className="fas fa-user-circle fa-6x"></i>
                  <h3 className="dark-grey-text mb-3 mt-3"><strong>Sign in</strong></h3>
                </div>
                <MDBInput label="Your email" group type="email" validate error="wrong" success="right" name='username' onChange={this.handleFormChange} onClick={() => { this.setState({ error: "" }) }} />
                <MDBInput label="Your password" group type="password" validate containerClass="mb-0" name='password' onKeyPress={this.onKeyUp} onChange={this.handleFormChange} onClick={() => { this.setState({ error: "" }) }} />

                <div className="text-center">
                  <p style={{ color: 'red', height: '25px' }}>{this.state.error}</p>
                </div>

                <div className="text-center pt-3 mb-3">
                  <MDBBtn type="button" gradient="blue" rounded className="btn-block z-depth-1a" onClick={this.login}>Sign in</MDBBtn>
                </div>

              </MDBCardBody>
            </MDBCard>

          </MDBCol>
        </MDBRow>
      </div>

    );
  }
}

export default Login;