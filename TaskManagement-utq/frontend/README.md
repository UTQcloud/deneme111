# Task Management System - Frontend

React tabanlı Task Management projesi

## 🚀 Kurulum ve Çalıştırma

```bash
# Bağımlılıkları yükle
npm install

# Geliştirme sunucusunu başlat
npm run dev

# Üretim için build al
npm run build
```

## 📦 Kullanılan Teknolojiler

- **React 19.1.1** - UI kütüphanesi
- **Vite 7.2.2** - Build tool ve dev server
- **Axios 1.7.2** - HTTP client
- **React Router DOM 6.26.0** - Routing (final için)

## 📂 Proje Yapısı

```
src/
├── components/
│   ├── Auth.jsx           # Login/Register sayfası
│   ├── Auth.css
│   ├── Dashboard.jsx      # Ana görev listesi
│   └── Dashboard.css
├── services/
│   └── api.js            # API servisleri
├── App.jsx               # Ana component
└── main.jsx             # Entry point
```

## 🔌 API Endpoints (Mevcut Backend)

- **POST** `/rest/api/auth/save` - Kullanıcı kaydı
- **POST** `/rest/api/auth/login` - Kullanıcı girişi
- **POST** `/rest/api/tasks` - Görev oluşturma

> **Not:** Backend ekibi endpoint'leri `/api/auth/register` ve `/api/auth/login` olarak değiştirirse, sadece `src/services/api.js` dosyasındaki URL'ler güncellenecek.

## ✅ Vize İçin Tamamlananlar

- [x] Login/Register sayfası
- [x] Form validation
- [x] API entegrasyonu (axios)
- [x] Hata yönetimi (invalid login, user not found)
- [x] Dashboard skeleton (task listesi draft)
- [x] Responsive tasarım
- [x] Status bazlı renklendirme
- [x] Due date uyarıları

## 📸 Ekran Görüntüleri

1. **Login Sayfası** - http://localhost:5173
2. **Register Formu** - Register tab'ine tıkla
3. **Dashboard** - Başarılı login sonrası
4. **Hata Mesajları** - Yanlış şifre ile dene

## 📋 Vize Sunumu Checklist

- [ ] Backend çalışıyor (mvn spring-boot:run)
- [ ] Frontend çalışıyor (npm run dev)
- [ ] Database bağlantısı aktif
- [ ] Yeni kullanıcı kayıt edebiliyorum
- [ ] Login çalışıyor
- [ ] Yanlış şifre ile hata alıyorum
- [ ] Dashboard görünüyor

---

## React Compiler

The React Compiler is not enabled on this template because of its impact on dev & build performances. To add it, see [this documentation](https://react.dev/learn/react-compiler/installation).

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.
