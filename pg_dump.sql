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
-- Name: commit; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.commit (
    id integer NOT NULL,
    description text NOT NULL,
    announcer character varying(50) NOT NULL,
    receiver character varying(50) NOT NULL,
    update_time text NOT NULL,
    card_id integer NOT NULL,
    is_read boolean DEFAULT false NOT NULL
);


ALTER TABLE public.commit OWNER TO postgres;

--
-- Name: commit_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.commit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.commit_id_seq OWNER TO postgres;

--
-- Name: commit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.commit_id_seq OWNED BY public.commit.id;


--
-- Name: commit id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commit ALTER COLUMN id SET DEFAULT nextval('public.commit_id_seq'::regclass);


--
-- Data for Name: commit; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.commit (id, description, announcer, receiver, update_time, card_id, is_read) FROM stdin;
2	test1	jianglianEin	jianglianZwei	1581847605897	1	t
3	test1	jianglianEin	jianglianZwei	1581848286750	2	f
\.


--
-- Name: commit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.commit_id_seq', 3, true);


--
-- Name: commit commit_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commit
    ADD CONSTRAINT commit_pk PRIMARY KEY (id);


--
-- Name: commit_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX commit_id_uindex ON public.commit USING btree (id);


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

