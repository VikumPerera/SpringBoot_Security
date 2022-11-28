INSERT INTO action(id, name, code, created_date, modified_date)
VALUES 	(1, 'Add', 'ADD', 1669612515000, 1669612515000),
	(2, 'View', 'VIEW', 1669612515000, 1669612515000),
	(3, 'View List', 'VIEW_LIST', 1669612515000, 1669612515000),
	(4, 'Edit', 'EDIT', 1669612515000, 1669612515000),
	(5, 'Delete', 'DELETE', 1669612515000, 1669612515000);
	
INSERT INTO component(id, name, code, created_date, modified_date)
VALUES 	(1, 'Merchant', 'MERCHANT', 1669612515000, 1669612515000);

INSERT INTO permission(id, code, created_date, modified_date, action_id, component_id)
VALUES 	(1, 'ADD_MERCHANT', 1669612515000, 1669612515000, 1, 1),
	(2, 'VIEW_MERCHANT', 1669612515000, 1669612515000, 2, 1),
	(3, 'VIEW_LIST_MERCHANT', 1669612515000, 1669612515000, 3, 1),
	(4, 'EDIT_MERCHANT', 1669612515000, 1669612515000, 4, 1),
	(5, 'DELETE_MERCHANT', 1669612515000, 1669612515000, 5, 1);
	
INSERT INTO role(id, name, code, created_date, modified_date)
VALUES 	(1, 'Admin', 'ADMIN', 1669612515000, 1669612515000),
	(2, 'Syatem User', 'SYSTEM_USER', 1669612515000, 1669612515000);
	
INSERT INTO role_permission(id, created_date, modified_date, permission_id, role_id)
VALUES 	(1, 1669612515000, 1669612515000, 1, 1),
	(2, 1669612515000, 1669612515000, 2, 1),
	(3, 1669612515000, 1669612515000, 3, 1),
	(4, 1669612515000, 1669612515000, 4, 1),
	(5, 1669612515000, 1669612515000, 5, 1);
	
INSERT INTO role_permission(id, created_date, modified_date, permission_id, role_id)
VALUES 	(6, 1669612515000, 1669612515000, 1, 2),
	(7, 1669612515000, 1669612515000, 2, 2),
	(8, 1669612515000, 1669612515000, 3, 2);
	

INSERT INTO user(id, email, enabled, first_name, last_name, password, created_date, modified_date)
VALUES 	(1, 'admin@wearenoetic.com', true, 'Noetic', 'Admin', '$2a$11$PPXLqMx8o4jLn/r2ZhJgo.kjw8DJzH5MbPPv47lbjX3B6EhMM61U2', 1669612515000, 1669612515000);
	

	
INSERT INTO user_role(id, created_date, modified_date, role_id, user_id)
VALUES 	(1, 1669612515000, 1669612515000, 1, 1);
