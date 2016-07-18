CREATE TABLE fb_page ( 
	id                   varchar(50)  NOT NULL,
	name                 varchar(100),
	description          varchar(1000),
	profile_pic_url      varchar(256),
	CONSTRAINT pk_fb_page PRIMARY KEY ( id )
 );
 

COMMENT ON COLUMN fb_page.id IS 'id of facebook fun page';

COMMENT ON COLUMN fb_page.name IS 'name of facebook fun page';

COMMENT ON COLUMN fb_page.description IS 'description of facebook fun page';

COMMENT ON COLUMN fb_page.profile_pic_url IS 'url of picture set as page`s profile picture';


CREATE TABLE fb_user ( 
	id                   varchar(50)  NOT NULL,
	name                 varchar(100),
	gender               varchar(10),
	profile_pic_url      varchar(256),
	CONSTRAINT pk_fb_user PRIMARY KEY ( id )
 );
 

COMMENT ON COLUMN fb_user.id IS 'facebook ID of user';

COMMENT ON COLUMN fb_user.name IS 'name of facebook user';

COMMENT ON COLUMN fb_user.gender IS 'gender of facebook user';

COMMENT ON COLUMN fb_user.profile_pic_url IS 'url adress of user`s profile picture';


CREATE TABLE jt_fb_user_page ( 
	user_id              varchar(50)  NOT NULL,
	page_id              varchar(50)  NOT NULL
 );

 
CREATE INDEX idx_jt_fb_user_page_user ON jt_fb_user_page ( user_id );

CREATE INDEX idx_jt_fb_user_page_page ON jt_fb_user_page ( page_id );


COMMENT ON TABLE jt_fb_user_page IS 'join table to determine which user like which pages and vice versa';

COMMENT ON COLUMN jt_fb_user_page.user_id IS 'id of facebook user';

COMMENT ON COLUMN jt_fb_user_page.page_id IS 'id of facebook fun page liked';


ALTER TABLE jt_fb_user_page ADD CONSTRAINT fk_jt_fb_user_page_fb_user FOREIGN KEY ( user_id ) REFERENCES fb_user( id ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE jt_fb_user_page ADD CONSTRAINT fk_jt_fb_user_page_fb_page FOREIGN KEY ( page_id ) REFERENCES fb_page( id ) ON DELETE CASCADE ON UPDATE CASCADE;

