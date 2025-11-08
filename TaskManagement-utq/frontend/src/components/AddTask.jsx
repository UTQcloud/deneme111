import { useState } from 'react';
import './AddTask.css';

export default function AddTask({ onCreate }) {
  const [open, setOpen] = useState(false);
  const [form, setForm] = useState({
    title: '',
    description: '',
    category: 'Work',
    status: 'Pending',
    dueDate: '',
    dueTime: ''
  });

  const change = (e) => {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: value }));
  };

  const submit = (e) => {
    e.preventDefault();
    if (!form.title.trim()) return alert('Title zorunlu.');
    if (!form.dueDate) return alert('Due date zorunlu.');

    onCreate({
      title: form.title.trim(),
      description: form.description,
      category: form.category,
      status: form.status,
      dueDate: form.dueDate,
      dueTime: form.dueTime
    });

    setOpen(false);
    setForm({
      title: '',
      description: '',
      category: 'Work',
      status: 'Pending',
      dueDate: '',
      dueTime: ''
    });
  };

  return (
    <>
      {/* Dashboard.css'teki .btn-add stilini kullanıyoruz */}
      <button className="btn-add" onClick={() => setOpen(true)}>+ Add Task</button>

      {open && (
        <div className="modal-backdrop" onClick={() => setOpen(false)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h3>Add Task</h3>

            <form onSubmit={submit} className="task-form">
              <div className="form-row">
                <label>Title</label>
                <input
                  name="title"
                  placeholder="e.g. Write report"
                  value={form.title}
                  onChange={change}
                  required
                />
              </div>

              <div className="form-row">
                <label>Description</label>
                <textarea
                  name="description"
                  rows={3}
                  value={form.description}
                  onChange={change}
                />
              </div>

              <div className="form-row two-col">
                <div>
                  <label>Category</label>
                  <select name="category" value={form.category} onChange={change}>
                    <option>Work</option>
                    <option>Personal</option>
                    <option>School</option>
                  </select>
                </div>
                <div>
                  <label>Status</label>
                  <select name="status" value={form.status} onChange={change}>
                    <option>Pending</option>
                    <option>In Progress</option>
                    <option>Completed</option>
                  </select>
                </div>
              </div>

              <div className="form-row two-col">
                <div>
                  <label>Due date</label>
                  <input
                    type="date"
                    name="dueDate"
                    value={form.dueDate}
                    onChange={change}
                    required
                  />
                </div>
                <div>
                  <label>Time</label>
                  <input
                    type="time"
                    name="dueTime"
                    value={form.dueTime}
                    onChange={change}
                  />
                </div>
              </div>

              <div className="actions">
                <button type="button" className="btn-secondary" onClick={() => setOpen(false)}>
                  Cancel
                </button>
                <button type="submit" className="btn-primary">
                  Save
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </>
  );
}
