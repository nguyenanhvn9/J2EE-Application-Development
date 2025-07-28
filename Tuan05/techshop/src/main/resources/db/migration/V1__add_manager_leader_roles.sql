-- Migration script to add MANAGER and LEADER roles
-- This script updates existing role values to use the new enum format

-- Update existing role values to use the new enum format
-- Note: This is handled automatically by Hibernate with @Enumerated(EnumType.STRING)
-- The existing 'USER' and 'ADMIN' values will continue to work

-- If you need to add sample users with new roles, you can use:
-- INSERT INTO users (username, password, full_name, email, phone, role, is_active) 
-- VALUES ('manager1', '$2a$10$...', 'Manager One', 'manager1@techshop.com', '0123456789', 'MANAGER', true);
-- INSERT INTO users (username, password, full_name, email, phone, role, is_active) 
-- VALUES ('leader1', '$2a$10$...', 'Leader One', 'leader1@techshop.com', '0123456789', 'LEADER', true); 