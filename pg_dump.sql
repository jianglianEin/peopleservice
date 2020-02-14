--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.20
-- Dumped by pg_dump version 9.6.16

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: team; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team (
    id integer NOT NULL,
    creator character varying(50) NOT NULL,
    teamname character varying(50) NOT NULL,
    description text
);


ALTER TABLE public.team OWNER TO postgres;

--
-- Name: team_teamid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.team_teamid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.team_teamid_seq OWNER TO postgres;

--
-- Name: team_teamid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.team_teamid_seq OWNED BY public.team.id;


--
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    username character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    icon character varying(100),
    email character varying(100) NOT NULL,
    power integer DEFAULT 1,
    id integer NOT NULL
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_id_seq OWNER TO postgres;

--
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_id_seq OWNED BY public."user".id;


--
-- Name: user_team_relation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_team_relation (
    username character varying(50) NOT NULL,
    team_id integer NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.user_team_relation OWNER TO postgres;

--
-- Name: user_team_relation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_team_relation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_team_relation_id_seq OWNER TO postgres;

--
-- Name: user_team_relation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_team_relation_id_seq OWNED BY public.user_team_relation.id;


--
-- Name: team id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team ALTER COLUMN id SET DEFAULT nextval('public.team_teamid_seq'::regclass);


--
-- Name: user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user" ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);


--
-- Name: user_team_relation id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_team_relation ALTER COLUMN id SET DEFAULT nextval('public.user_team_relation_id_seq'::regclass);


--
-- Data for Name: team; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.team (id, creator, teamname, description) FROM stdin;
\.


--
-- Name: team_teamid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.team_teamid_seq', 8, true);


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."user" (username, password, icon, email, power, id) FROM stdin;
jianglianEin	Ftjs1998226@		913057041@qq.com	1	1
\.


--
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_id_seq', 11, true);


--
-- Data for Name: user_team_relation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_team_relation (username, team_id, id) FROM stdin;
\.


--
-- Name: user_team_relation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_team_relation_id_seq', 7, true);


--
-- Name: team team_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_pk PRIMARY KEY (id);


--
-- Name: user user_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pk PRIMARY KEY (username);


--
-- Name: user_team_relation user_team_relation_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_team_relation
    ADD CONSTRAINT user_team_relation_pk PRIMARY KEY (id);


--
-- Name: user_email_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX user_email_uindex ON public."user" USING btree (email);


--
-- Name: user_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX user_id_uindex ON public."user" USING btree (id);


--
-- Name: user_team_relation_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX user_team_relation_id_uindex ON public.user_team_relation USING btree (id);


--
-- Name: team team_user_username_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_user_username_fk FOREIGN KEY (creator) REFERENCES public."user"(username) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: user_team_relation user_team_relation_team_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_team_relation
    ADD CONSTRAINT user_team_relation_team_id_fk FOREIGN KEY (team_id) REFERENCES public.team(id) ON DELETE CASCADE;


--
-- Name: user_team_relation user_team_relation_user_username_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_team_relation
    ADD CONSTRAINT user_team_relation_user_username_fk FOREIGN KEY (username) REFERENCES public."user"(username);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

