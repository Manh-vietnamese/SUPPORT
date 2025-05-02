**Hướng Dẫn Sử Dụng Plugin SupportServer**  
*Phiên bản 1.0 - Dành cho Minecraft Server*  

---

### **Mục Lục**
1. **Giới Thiệu**  
2. **Tính Năng Chính**  
3. **Cài Đặt Plugin**  
4. **Cấu Hình Plugin**  
   - 4.1. File `config.yml`  
   - 4.2. File `messages.yml`  
5. **Lệnh và Quyền Hạn**  
6. **Tùy Chỉnh Nâng Cao**  
7. **Xử Lý Lỗi Thường Gặp**  

---

### **1. Giới Thiệu**  
Plugin **SupportServer** giúp quản lý thời gian hồi chiêu (cooldown) khi người chơi bị tấn công và chặn các lệnh cụ thể trong thời gian này. Plugin phù hợp cho các server PvP hoặc cần cân bằng gameplay.

---

### **2. Tính Năng Chính**  
- **Áp dụng cooldown** khi người chơi nhận sát thương.  
- **Hiển thị thời gian cooldown** qua thanh Action Bar.  
- **Chặn lệnh** đã cấu hình trong thời gian cooldown.  
- **Tự động xóa cooldown** khi người chơi chết.  
- **Hỗ trợ tùy chỉnh thông báo và mã màu**.  

---

### **3. Cài Đặt Plugin**  
1. **Yêu Cầu**:  
   - Minecraft Server phiên bản 1.21+ (Purpur/Paper/Spigot).  
   - Plugin **ProtocolLib** (để hiển thị Action Bar).  

2. **Cài Đặt**:  
   - Tải file `SupportServer.jar` và đặt vào thư mục `plugins/`.  
   - Khởi động lại server để plugin tạo file cấu hình.  

---

### **4. Cấu Hình Plugin**  
#### **4.1. File `config.yml`**  
```yaml
# Thời gian cooldown mặc định (giây)
cooldown-seconds: 30

# Danh sách lệnh bị chặn trong cooldown
blocked-commands:
  - msg
  - pl
  - w
  - tpa
```  

- **cooldown-seconds**: Thời gian chờ trước khi có thể dùng lệnh.  
- **blocked-commands**: Các lệnh bị chặn.  

#### **4.2. File `messages.yml`**  
```yaml
# Thông báo cooldown
cooldown-message: "§cVui lòng đợi §e%time%§c giây!"

# Thông báo không thể dùng lệnh
no_command: "&cBạn không thể dùng lệnh này ngay bây giờ!"

# Thông báo reload plugin
Plugin_reloaded: "&bPlugin đã reload thành công!"
```  

- Sử dụng `&` hoặc `§` để định dạng màu.  
- `%time%`: Placeholder hiển thị thời gian còn lại.  

---

### **5. Lệnh và Quyền Hạn**  
- **Lệnh**:  
  - `/supportserver reload`: Reload lại cấu hình plugin.  
    - **Quyền**: `Sunflower.SP.admin`  

- **Phân Quyền**:  
  - Để bỏ qua cooldown, gán quyền `Sunflower.SP.admin` cho người chơi hoặc nhóm (dùng LuckPerms).  
  ```yaml
  /lp group admin permission set Sunflower.SP.admin
  ```

---

### **6. Tùy Chỉnh Nâng Cao**  
#### **Thêm/Xóa Lệnh Bị Chặn**  
- Mở file `config.yml`, chỉnh sửa danh sách `blocked-commands`.  
- Ví dụ thêm lệnh `home`:  
  ```yaml
  blocked-commands:
    - msg
    - home
  ```  

#### **Thay Đổi Màu Thông Báo**  
- Sửa file `messages.yml` và dùng mã màu Minecraft:  
  ```yaml
  cooldown-message: "§aChờ §6%time% §agiây!"
  ```  

---

### **7. Xử Lý Lỗi Thường Gặp**  
| **Lỗi** | **Nguyên Nhân** | **Cách Khắc Phục** |  
|---------|------------------|---------------------|  
| Không hiển thị Action Bar | Thiếu ProtocolLib | Cài đặt ProtocolLib |  
| Thông báo không có màu | Sai định dạng mã màu | Dùng `§` hoặc `&` trong `messages.yml` |  
| Lệnh không bị chặn | Lỗi cấu hình | Kiểm tra tên lệnh trong `blocked-commands` |  

---

**Liên Hệ Hỗ Trợ**  
- Discord: `support.sunflower`  
- Email: `support@sunflower.com`  

*Chúc bạn trải nghiệm plugin vui vẻ! 🌻*
