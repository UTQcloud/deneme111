import { useState } from 'react';
import { authService } from '../services/api';
import './Auth.css';

function Auth({ onLoginSuccess }) {
  const [isLogin, setIsLogin] = useState(true);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  // Login form state
  const [loginData, setLoginData] = useState({
    mail: '',
    password: '',
  });

  // Register form state
  const [registerData, setRegisterData] = useState({
    firstName: '',
    lastName: '',
    birthDate: '',
    mail: '',
    phone: '',
    password: '',
  });

  const handleLoginChange = (e) => {
    setLoginData({ ...loginData, [e.target.name]: e.target.value });
    setError('');
  };

  const handleRegisterChange = (e) => {
    setRegisterData({ ...registerData, [e.target.name]: e.target.value });
    setError('');
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    const result = await authService.login(loginData);

    setLoading(false);

    if (result.success) {
      setSuccess('Login successful!');
      localStorage.setItem('user', JSON.stringify(result.data));
      setTimeout(() => onLoginSuccess(result.data), 1000);
    } else {
      setError(result.error);
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    setSuccess('');

    const result = await authService.register(registerData);

    setLoading(false);

    if (result.success) {
      setSuccess('Registration successful! You can now login.');
      setTimeout(() => {
        setIsLogin(true);
        setRegisterData({
          firstName: '',
          lastName: '',
          birthDate: '',
          mail: '',
          phone: '',
          password: '',
        });
      }, 2000);
    } else {
      setError(result.error);
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h1>Task Management System</h1>
        
        <div className="auth-tabs">
          <button
            className={isLogin ? 'active' : ''}
            onClick={() => {
              setIsLogin(true);
              setError('');
              setSuccess('');
            }}
          >
            Login
          </button>
          <button
            className={!isLogin ? 'active' : ''}
            onClick={() => {
              setIsLogin(false);
              setError('');
              setSuccess('');
            }}
          >
            Register
          </button>
        </div>

        {error && <div className="alert alert-error">{error}</div>}
        {success && <div className="alert alert-success">{success}</div>}

        {isLogin ? (
          <form onSubmit={handleLogin} className="auth-form">
            <div className="form-group">
              <label>Email</label>
              <input
                type="email"
                name="mail"
                value={loginData.mail}
                onChange={handleLoginChange}
                required
                placeholder="Enter your email"
              />
            </div>

            <div className="form-group">
              <label>Password</label>
              <input
                type="password"
                name="password"
                value={loginData.password}
                onChange={handleLoginChange}
                required
                placeholder="Enter your password"
              />
            </div>

            <button type="submit" className="btn-primary" disabled={loading}>
              {loading ? 'Logging in...' : 'Login'}
            </button>
          </form>
        ) : (
          <form onSubmit={handleRegister} className="auth-form">
            <div className="form-row">
              <div className="form-group">
                <label>First Name</label>
                <input
                  type="text"
                  name="firstName"
                  value={registerData.firstName}
                  onChange={handleRegisterChange}
                  required
                  placeholder="First name"
                />
              </div>

              <div className="form-group">
                <label>Last Name</label>
                <input
                  type="text"
                  name="lastName"
                  value={registerData.lastName}
                  onChange={handleRegisterChange}
                  required
                  placeholder="Last name"
                />
              </div>
            </div>

            <div className="form-group">
              <label>Birth Date</label>
              <input
                type="date"
                name="birthDate"
                value={registerData.birthDate}
                onChange={handleRegisterChange}
                required
              />
            </div>

            <div className="form-group">
              <label>Email</label>
              <input
                type="email"
                name="mail"
                value={registerData.mail}
                onChange={handleRegisterChange}
                required
                placeholder="Enter your email"
              />
            </div>

            <div className="form-group">
              <label>Phone</label>
              <input
                type="tel"
                name="phone"
                value={registerData.phone}
                onChange={handleRegisterChange}
                required
                maxLength="11"
                placeholder="05XXXXXXXXX"
              />
            </div>

            <div className="form-group">
              <label>Password</label>
              <input
                type="password"
                name="password"
                value={registerData.password}
                onChange={handleRegisterChange}
                required
                minLength="6"
                placeholder="Minimum 6 characters"
              />
            </div>

            <button type="submit" className="btn-primary" disabled={loading}>
              {loading ? 'Registering...' : 'Register'}
            </button>
          </form>
        )}
      </div>
    </div>
  );
}

export default Auth;
