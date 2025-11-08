import { useState, useEffect } from 'react';
import './Dashboard.css';
import AddTask from '../components/AddTask'; // <- yolunu projenin yapısına göre ayarla
import { taskService } from '../services/api';

function Dashboard({ user, onLogout }) {
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const normalizeTimeValue = (time) => {
    if (time === null || time === undefined || time === '') {
      return null;
    }

    if (typeof time === 'string') {
      return time.length > 5 ? time.slice(0, 5) : time;
    }

    if (Array.isArray(time) && time.length >= 2) {
      const [hour, minute] = time;
      return `${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}`;
    }

    return `${time}`;
  };

  const fetchTasks = async () => {
    setLoading(true);
    const result = await taskService.getAllTasks();

    if (result.success) {
      setTasks(result.data || []);
      setError('');
    } else {
      setError(result.error);
      setTasks([]);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchTasks();
  }, []);

  const getStatusColor = (status) => {
    switch (status) {
      case 'Completed': return 'status-completed';
      case 'In Progress': return 'status-progress';
      case 'Pending': return 'status-pending';
      default: return '';
    }
  };

  // dueTime'ı da dikkate al
  const isDueSoon = (dueDate, dueTime) => {
    if (!dueDate) return false;
    const normalizedTime = normalizeTimeValue(dueTime) || '23:59';
    const due = new Date(`${dueDate}T${normalizedTime}`);
    const now = new Date();
    const diffDays = (due - now) / (1000 * 60 * 60 * 24);
    return diffDays <= 2 && diffDays >= 0;
  };

  // AddTask bileşeninden gelen veriyi state'e ekle
  const handleCreateTask = async (data) => {
    const result = await taskService.createTask(data);

    if (result.success) {
      setTasks((prev) => [...prev, result.data]);
      setError('');
      return { success: true };
    }

    setError(result.error);
    return { success: false, error: result.error };
  };

  const formatTime = (time) => {
    const normalized = normalizeTimeValue(time);
    return normalized || '—';
  };

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <div>
          <h1>Task Dashboard</h1>
          <p>Welcome back, {user?.firstName} {user?.lastName}!</p>
        </div>
        <button onClick={onLogout} className="btn-logout">Logout</button>
      </header>

      <div className="stats-container">
        <div className="stat-card">
          <h3>Total Tasks</h3>
          <p className="stat-number">{tasks.length}</p>
        </div>
        <div className="stat-card">
          <h3>Completed</h3>
          <p className="stat-number completed">{tasks.filter(t => t.status === 'Completed').length}</p>
        </div>
        <div className="stat-card">
          <h3>In Progress</h3>
          <p className="stat-number progress">{tasks.filter(t => t.status === 'In Progress').length}</p>
        </div>
        <div className="stat-card">
          <h3>Pending</h3>
          <p className="stat-number pending">{tasks.filter(t => t.status === 'Pending').length}</p>
        </div>
      </div>

      <div className="tasks-section">
        <div className="section-header">
          <h2>Your Tasks</h2>
          {/* Eski <button className="btn-add"> yerine */}
          <AddTask onCreate={handleCreateTask} />
        </div>

        {error && (
          <div className="alert-due" role="alert">
            {error}
          </div>
        )}

        {loading ? (
          <div className="tasks-grid">
            <div className="task-card">
              <p>Loading tasks...</p>
            </div>
          </div>
        ) : (
          <div className="tasks-grid">
            {tasks.map((task) => (
              <div
                key={task.id}
                className={`task-card ${isDueSoon(task.dueDate, task.dueTime) ? 'due-soon' : ''}`}
              >
                <div className="task-header">
                  <h3>{task.title}</h3>
                  <span className={`status-badge ${getStatusColor(task.status)}`}>{task.status}</span>
                </div>
                <p className="task-description">{task.description}</p>
                <div className="task-meta">
                  <span className="category-badge">{task.category}</span>
                  <span className="due-date">📅 {task.dueDate} at {formatTime(task.dueTime)}</span>
                </div>
                {isDueSoon(task.dueDate, task.dueTime) && (
                  <div className="alert-due">⚠️ Due soon!</div>
                )}
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default Dashboard;
