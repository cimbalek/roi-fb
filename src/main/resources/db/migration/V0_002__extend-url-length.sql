ALTER TABLE fb_page ALTER COLUMN profile_pic_url TYPE varchar(2084);
ALTER TABLE fb_page ALTER COLUMN name TYPE varchar(512);
ALTER TABLE fb_page ALTER COLUMN description TYPE varchar(10000);

ALTER TABLE fb_user ALTER COLUMN profile_pic_url TYPE varchar(2084);
ALTER TABLE fb_user ALTER COLUMN name TYPE varchar(512);
