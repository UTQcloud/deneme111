import { useState } from 'react';
import './AddTask.css';

export default function AddTask({ onCreate }) {
  const [open, setOpen] = useState(false);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState('');
  const [form, setForm] = useState({
    title: '',
    description: '',
    category: 'Work',
    status: 'Pending',
    dueDate: '',
    dueTime: ''
  });

  const resetForm = () => {
    setForm({
      title: '',
      description: '',
      category: 'Work',
      status: 'Pending',
      dueDate: '',
      dueTime: ''
    });
  };

  const change = (e) => {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: value }));
  };

  const closeModal = () => {
    setOpen(false);
    setError('');
    setSaving(false);
    resetForm();
  };

  const submit = async (e) => {
    e.preventDefault();
    setError('');

    if (!form.title.trim()) {
      setError('Title zorunlu.');
      return;
    }

    if (!form.dueDate) {
      setError('Due date zorunlu.');
      return;
    }

    setSaving(true);
    const result = await onCreate({
      title: form.title.trim(),
      description: form.description,
      category: form.category,
      status: form.status,
      dueDate: form.dueDate,
      dueTime: form.dueTime
    });
    setSaving(false);

    if (!result?.success) {
      setError(result?.error || 'Task kaydedilemedi.');
      return;
    }

    closeModal();
  };

  return (
    <>
      {/* Dashboard.css'teki .btn-add stilini kullanıyoruz */}
      <button className="btn-add" onClick={() => { setOpen(true); setError(''); resetForm(); }}>+ Add Task</button>

      {open && (
        <div className="modal-backdrop" onClick={closeModal}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h3>Add Task</h3>

            <form onSubmit={submit} className="task-form">
              {error && (
                <div className="alert-error" role="alert">
                  {error}
                </div>
              )}
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
                <button type="button" className="btn-secondary" onClick={closeModal}>
                  Cancel
                </button>
                <button type="submit" className="btn-primary" disabled={saving}>
                  {saving ? 'Saving...' : 'Save'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </>
  );
}
