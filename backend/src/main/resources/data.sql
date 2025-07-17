INSERT INTO roles(id,name) VALUES(UUID(), 'ROLE_ADMIN');
INSERT INTO roles(id,name) VALUES(UUID(), 'ROLE_STUDENT');

INSERT INTO users(id,username,password,enabled,created_at,updated_at) VALUES(UUID(),'admin','$2a$10$Dow1OX3N0NnfdXbra.mzeuFi1LXxax3CX6XcncxZV8oPZxC25f6/S',true,NOW(),NOW());

INSERT INTO user_roles(user_id,role_id)
SELECT u.id,r.id FROM users u, roles r WHERE u.username='admin' AND r.name='ROLE_ADMIN';

INSERT INTO courses(id,title,cover,description,created_at,updated_at) VALUES(UUID(),'Sample Course','cover.jpg','demo description',NOW(),NOW());
