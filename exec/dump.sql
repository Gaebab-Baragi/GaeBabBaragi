--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3 (Debian 15.3-1.pgdg120+1)
-- Dumped by pg_dump version 15.3 (Debian 15.3-1.pgdg120+1)

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

SET default_table_access_method = heap;

--
-- Name: bookmark; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bookmark (
    id bigint NOT NULL,
    member_id bigint NOT NULL,
    recipe_id bigint NOT NULL
);


ALTER TABLE public.bookmark OWNER TO postgres;

--
-- Name: bookmark_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bookmark_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.bookmark_id_seq OWNER TO postgres;

--
-- Name: bookmark_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bookmark_id_seq OWNED BY public.bookmark.id;


--
-- Name: comment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comment (
    id bigint NOT NULL,
    member_id bigint NOT NULL,
    recipe_id bigint NOT NULL,
    write_time timestamp(6) without time zone,
    comment_img_key character varying(255),
    comment_img_url character varying(255),
    content character varying(255)
);


ALTER TABLE public.comment OWNER TO postgres;

--
-- Name: comment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.comment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.comment_id_seq OWNER TO postgres;

--
-- Name: comment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comment_id_seq OWNED BY public.comment.id;


--
-- Name: forbidden; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.forbidden (
    id bigint NOT NULL,
    ingredient_id bigint,
    pet_id bigint
);


ALTER TABLE public.forbidden OWNER TO postgres;

--
-- Name: forbidden_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.forbidden_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.forbidden_id_seq OWNER TO postgres;

--
-- Name: forbidden_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.forbidden_id_seq OWNED BY public.forbidden.id;


--
-- Name: ingredient; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ingredient (
    edible boolean,
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.ingredient OWNER TO postgres;

--
-- Name: ingredient_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ingredient_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ingredient_id_seq OWNER TO postgres;

--
-- Name: ingredient_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ingredient_id_seq OWNED BY public.ingredient.id;


--
-- Name: ingredient_recipe_ingredients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ingredient_recipe_ingredients (
    ingredient_id bigint NOT NULL,
    recipe_ingredients_id bigint NOT NULL
);


ALTER TABLE public.ingredient_recipe_ingredients OWNER TO postgres;

--
-- Name: meeting; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.meeting (
    current_participants integer NOT NULL,
    max_participant integer,
    id bigint NOT NULL,
    member_id bigint,
    recipe_id bigint,
    start_time timestamp(6) without time zone,
    description character varying(255),
    password character varying(255),
    status character varying(255),
    title character varying(255) NOT NULL,
    CONSTRAINT meeting_status_check CHECK (((status)::text = ANY ((ARRAY['SCHEDULED'::character varying, 'ATTENDEE_WAIT'::character varying, 'IN_PROGRESS'::character varying])::text[])))
);


ALTER TABLE public.meeting OWNER TO postgres;

--
-- Name: meeting_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.meeting_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.meeting_id_seq OWNER TO postgres;

--
-- Name: meeting_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.meeting_id_seq OWNED BY public.meeting.id;


--
-- Name: member; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.member (
    id bigint NOT NULL,
    register_date timestamp(6) without time zone,
    nickname character varying(255) NOT NULL,
    password character varying(255),
    profile_url character varying(255),
    refresh_token character varying(255),
    role character varying(32) DEFAULT 'GUEST'::character varying,
    username character varying(255) NOT NULL,
    CONSTRAINT member_role_check CHECK (((role)::text = ANY ((ARRAY['GUEST'::character varying, 'USER'::character varying])::text[])))
);


ALTER TABLE public.member OWNER TO postgres;

--
-- Name: member_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.member_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.member_id_seq OWNER TO postgres;

--
-- Name: member_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.member_id_seq OWNED BY public.member.id;


--
-- Name: pet; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pet (
    id bigint NOT NULL,
    member_id bigint NOT NULL,
    name character varying(255) NOT NULL,
    s3key character varying(255),
    s3url character varying(255)
);


ALTER TABLE public.pet OWNER TO postgres;

--
-- Name: pet_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pet_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pet_id_seq OWNER TO postgres;

--
-- Name: pet_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pet_id_seq OWNED BY public.pet.id;


--
-- Name: recipe; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recipe (
    hit bigint,
    id bigint NOT NULL,
    member_id bigint,
    written_time timestamp(6) without time zone,
    description character varying(255),
    image_key character varying(255),
    image_url character varying(255),
    title character varying(255),
    video_key character varying(255),
    video_url character varying(255)
);


ALTER TABLE public.recipe OWNER TO postgres;

--
-- Name: recipe_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.recipe_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.recipe_id_seq OWNER TO postgres;

--
-- Name: recipe_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.recipe_id_seq OWNED BY public.recipe.id;


--
-- Name: recipe_ingredient; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recipe_ingredient (
    id bigint NOT NULL,
    ingredient_id bigint,
    recipe_id bigint,
    amount character varying(255)
);


ALTER TABLE public.recipe_ingredient OWNER TO postgres;

--
-- Name: recipe_ingredient_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.recipe_ingredient_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.recipe_ingredient_id_seq OWNER TO postgres;

--
-- Name: recipe_ingredient_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.recipe_ingredient_id_seq OWNED BY public.recipe_ingredient.id;


--
-- Name: step; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.step (
    id bigint NOT NULL,
    ordering_number bigint,
    recipe_id bigint,
    description character varying(255),
    s3key character varying(255),
    s3url character varying(255)
);


ALTER TABLE public.step OWNER TO postgres;

--
-- Name: step_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.step_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.step_id_seq OWNER TO postgres;

--
-- Name: step_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.step_id_seq OWNED BY public.step.id;


--
-- Name: bookmark id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bookmark ALTER COLUMN id SET DEFAULT nextval('public.bookmark_id_seq'::regclass);


--
-- Name: comment id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment ALTER COLUMN id SET DEFAULT nextval('public.comment_id_seq'::regclass);


--
-- Name: forbidden id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forbidden ALTER COLUMN id SET DEFAULT nextval('public.forbidden_id_seq'::regclass);


--
-- Name: ingredient id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredient ALTER COLUMN id SET DEFAULT nextval('public.ingredient_id_seq'::regclass);


--
-- Name: meeting id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.meeting ALTER COLUMN id SET DEFAULT nextval('public.meeting_id_seq'::regclass);


--
-- Name: member id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member ALTER COLUMN id SET DEFAULT nextval('public.member_id_seq'::regclass);


--
-- Name: pet id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pet ALTER COLUMN id SET DEFAULT nextval('public.pet_id_seq'::regclass);


--
-- Name: recipe id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe ALTER COLUMN id SET DEFAULT nextval('public.recipe_id_seq'::regclass);


--
-- Name: recipe_ingredient id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe_ingredient ALTER COLUMN id SET DEFAULT nextval('public.recipe_ingredient_id_seq'::regclass);


--
-- Name: step id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.step ALTER COLUMN id SET DEFAULT nextval('public.step_id_seq'::regclass);


--
-- Data for Name: bookmark; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.bookmark (id, member_id, recipe_id) FROM stdin;
\.


--
-- Data for Name: comment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.comment (id, member_id, recipe_id, write_time, comment_img_key, comment_img_url, content) FROM stdin;
\.


--
-- Data for Name: forbidden; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.forbidden (id, ingredient_id, pet_id) FROM stdin;
1	1	1
\.


--
-- Data for Name: ingredient; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ingredient (edible, id, name) FROM stdin;
f	1	사과
f	2	바나나
f	3	청경채
f	4	브로콜리
f	5	양배추
f	6	당근
f	7	닭 가슴살
f	8	계란
f	9	마늘
f	10	포도
f	11	우유
f	12	양파
f	13	감자
f	14	호박
f	15	시금치
f	16	고구마
f	17	두부
f	18	올리브 오일
f	19	플레인 요거트
f	20	다진 닭고기
f	21	현미밥
f	22	그린빈
f	23	마요네즈
f	24	배
f	25	소고기
f	26	돼지고기
f	27	통밀가루
f	28	연어
f	29	오트밀 가루
f	30	크림치즈
f	31	꿀
f	32	사과즙
f	33	베이킹파우더
f	34	코코넛 가루
f	35	식물성 오일
f	36	땅콩 버터
f	37	계피
f	38	레몬
f	39	파슬리
f	40	말린 켈프
f	41	말린 크렌베리
\.


--
-- Data for Name: ingredient_recipe_ingredients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ingredient_recipe_ingredients (ingredient_id, recipe_ingredients_id) FROM stdin;
\.


--
-- Data for Name: meeting; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.meeting (current_participants, max_participant, id, member_id, recipe_id, start_time, description, password, status, title) FROM stdin;
0	2	1	1	1	2023-08-17 09:34:42.945078	1	\N	ATTENDEE_WAIT	1
0	2	2	1	1	2023-08-17 09:34:42.947229	2	\N	SCHEDULED	2
\.


--
-- Data for Name: member; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.member (id, register_date, nickname, password, profile_url, refresh_token, role, username) FROM stdin;
1	\N	박영서	{bcrypt}$2a$10$GFSkICiwSFtsbnv2kft6XeDE.DR7tQuvcjYmweedQNoUZk/cqIP8i	/image/개밥바라기.png	\N	USER	test1@gmail.com
2	\N	김선형	{bcrypt}$2a$10$2Fjzd4SdlGjdZAQKbM7yA.jxWzy5vkWb5/nmUZd/BvOuPfF6IOb..	/image/개밥바라기.png	\N	USER	test2@gmail.com
3	\N	유승아	{bcrypt}$2a$10$XU5fGikHjBkVn2unc6FWtuR6Az3tRkVssGoL.BDIZ/.Rt13CFNTVq	/image/개밥바라기.png	\N	USER	test3@gmail.com
4	\N	Yeongseo Park	0rA%xpP@OSKc	https://sh-bucket.s3.ap-northeast-2.amazonaws.com/profile/1be20bde-97e5-3abc-b771-73a9497428d0	eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSZWZyZXNoVG9rZW4iLCJzYWx0IjoxNjkyMjM0NTM0MTgzLCJleHAiOjE2OTM0NDQxMzQsImlhdCI6MTY5MjIzNDUzNH0.H8XENOzdSbvRm7v5pMa2PdmFk3PCC3DZYySBRiy9twdzyZD9W0mrkqxOlGko5w799ktN21anABgLSduDHiROUg	USER	pj0642@gmail.com
\.


--
-- Data for Name: pet; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pet (id, member_id, name, s3key, s3url) FROM stdin;
1	1	지산	\N	\N
2	1	토리	\N	\N
3	1	아토	\N	\N
\.


--
-- Data for Name: recipe; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.recipe (hit, id, member_id, written_time, description, image_key, image_url, title, video_key, video_url) FROM stdin;
0	3	1	2023-08-17 09:34:42.757963	바나나와 오트밀 단 2가지로 만들어보는 강아지 쿠키 만들기!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220608/629ff8b90a8b0.png	바나나 오트밀 쿠키	\N	\N
0	5	1	2023-08-17 09:34:42.776573	생후 3주~4주의 아기 강아지들을 위한 맛있는 이유식 만들어보기!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220411/62538cb0d7615.png	아기 강아지들을 위한 - 강아지 이유식	\N	\N
0	6	1	2023-08-17 09:34:42.786223	섬유질과 비타민, 미네랄과 산화방지제 함량이 높고 단백질이 풍부한 재료들로 만들어보는 소고기와 배 정식!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220407/624e68ed396c8.jpeg	섬유질과 단백질이 풍부한 - 소고기와 배 정식	\N	\N
0	7	1	2023-08-17 09:34:42.798047	연어와 각종 채소로 만들어보는 맛있는 쿠키!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220406/624d03429bbf5.png	연어 쿠키	\N	\N
0	9	1	2023-08-17 09:34:42.818864	한입 크기로 먹기 좋은 댕댕이 크림치즈 쿠키 만들기!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220401/6246d03398b8f.jpeg	크림치즈 쿠키	\N	\N
0	10	1	2023-08-17 09:34:42.832827	맛있고 건강에 좋은 오트밀 코코넛 쿠키! 사랑 가득 담아서 만들어봐요!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/6245111e447dc.png	오트밀 코코넛 쿠키	\N	\N
0	11	3	2023-08-17 09:34:42.846423	우리 댕댕이들이 제일 좋아하는 닭가슴살 간식! 집에서 건조기 하나로 쉽게 만들어봐요!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/6245142c97d58.png	닭가슴살 말랭이	\N	\N
0	12	3	2023-08-17 09:34:42.852221	비타민과 미네랄, 섬유질의 훌륭한 공급원인 사과! 쉽고 빠르게 만들 수 있는 땅콩버터 사과 쿠키 만들기!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220330/6243d84726357.png	땅콩버터 사과 쿠키	\N	\N
0	13	1	2023-08-17 09:34:42.866096	티아민, 니아신, 리보플라빈, 비타민 B-6, 인, 단백질, 아연, 칼륨 등 건강에 필요한 영양소의 훌륭한 공급원인 돼지고기와 강아지에게 변비, 소화 흡수, 입냄새 제거에 효과적인 사과로 만들어 보는 간식!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220328/6241234727449.jpeg\n	돼지고기 사과 간식	\N	\N
0	14	2	2023-08-17 09:34:42.874994	비타민과 미네랄이 풍부한 댕댕이 최애간식 고구마! 고구마를 활용한 간단 고구마 과자 만들기!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220324/623c072045d77.png	고구마 과자	\N	\N
0	15	1	2023-08-17 09:34:42.881804	가장 먼저 감자는 빈혈을 예방해준다고 해요. 빈혈의 종류 중에서도 철결핍성 빈혈에 특히 도움이 되는데요. 감자에는 비타민C가 다량 함유되어 있고 이는 철과 결합하는 작용을 하기에 체내로의 흡수를 도와 빈혈을 방지해주는 효과를 볼 수 있답니다.	\N	https://recipe.bom.co.kr/uploads/posts/images/20190621/5d0c9a35985e6.jpeg	감자와 소고기	\N	\N
0	16	1	2023-08-17 09:34:42.888375	정말 뚝딱 맛도 좋고 건강에도 좋은 댕댕이 수제간식 만들기 ^^ 100점입니다아~	\N	https://recipe.bom.co.kr/uploads/posts/images/20190121/5c454ce960dc2.jpeg	사과 요거트 아이스크림 만들기	\N	\N
0	17	1	2023-08-17 09:34:42.896055	댕댕이가 제일 좋아하는 닭가슴살과 건강한 오트밀로 만들어 보는 건강 정식!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/6259110e827b5.png	오트밀 닭고기 정식	\N	\N
0	18	1	2023-08-17 09:34:42.914461	강아지들은 단백질이 풍부한 음식을 섭취해야 합니다. 닭고기처럼 맛있는 음식을 강아지에게 대접할 이유가 많습니다. 닭고기는 강아지 훈련용 간식으로 제공하셔도 좋으며 위가 예민한 강아지에게 밥과 함께 제공하셔도 좋습니다.	\N	https://recipe.bom.co.kr/uploads/posts/images/20190404/5ca5a1bc28e5c.png	닭고기 비스킷	\N	\N
0	19	1	2023-08-17 09:34:42.927247	건조기를 이용한 닭가슴살 육포 만들기^^	\N	https://recipe.bom.co.kr/uploads/posts/images/20190118/5c415a7a46527.jpeg	닭가슴살 육포	\N	\N
0	20	1	2023-08-17 09:34:42.931316	다가오는 여름 땅콩버터와 바나나 등 건강하고 맛 좋은 재료를 만드는 시원한 아이스크림 간식!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220329/6242754ed893e.png	바나나 땅콩버터 아이스크림	\N	\N
1	8	1	2023-08-17 09:34:42.810131	대량으로 만들어 보관해두었다가 그때 그때 줄 수 있는 닭고기 츄! 3가지 재료 만으로 30분 만에 만들 수 있는 닭고기 츄 만들기!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220404/624a59fc2075c.jpeg	닭고기 츄	\N	\N
1	2	1	2023-08-17 09:34:42.751114	알러지가 있는 아이들, 방광 결석을 앓고 있는 아이들에게 최적의 간식 두부로 만드는 간식!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220531/62957dfbe5f0f.png	두부 간식	\N	\N
1	4	1	2023-08-17 09:34:42.763919	오븐이 필요 없는 건강한 단호박으로 만드는 간단 단호박 쿠키 만들기!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220527/62904f059b6f4.jpeg	단호박 쿠키	\N	\N
1	1	1	2023-08-17 09:34:42.724998	강아지에게 필요한 단백질이 듬뿍 들어간 그린빈과 함께 다양한 건강한 재료들로 만드는 정식!	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/625678e2b96a1.png	닭고기 채소 정식	\N	\N
\.


--
-- Data for Name: recipe_ingredient; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.recipe_ingredient (id, ingredient_id, recipe_id, amount) FROM stdin;
1	18	1	1 tbsp
2	19	1	2 tbsp
3	13	1	100g
4	20	1	100g
5	21	1	0.5g
6	22	1	50g
7	14	1	50g
8	17	2	50g
9	2	3	
10	14	4	1kg
11	8	4	1개
12	6	4	200g
13	11	5	1 cup
14	8	5	1개
15	19	5	1 cup
16	23	5	1 tbsp
17	6	6	50g
18	22	6	50g
19	24	6	100g
20	25	6	50g
21	27	7	1 cup
22	19	7	1 cup
23	28	7	70 g
24	11	8	1/4 cup
25	20	8	100 g
26	29	8	1/4 cup
27	27	9	1cup
28	30	9	1/4 cup
29	31	9	1 tbsp
30	32	9	2 tbsp
31	8	9	1 개
32	33	9	1/2 tbsp
33	8	10	1개
34	34	10	1/4 cup
35	27	10	1 cup
36	35	10	1/4 cup
37	31	10	2 tbsp
38	29	10	오트밀 가루
39	7	11	300g
40	8	12	1개
41	1	12	100 g
42	35	12	2 tbsp
43	36	12	2 tbsp
44	27	12	1 cup
45	31	12	1 tbsp
46	1	13	50g
47	26	13	100g
48	37	13	1/4 tbsp
49	16	14	100g
50	18	14	1 tbsp
51	38	15	1개
52	6	15	50g
53	13	15	100g
54	39	15	1tbsp
55	25	15	100g
56	1	16	100g
57	19	16	1cup
58	36	16	1tsp
59	31	16	1tsp
60	8	17	1개
61	18	17	1 tbsp
62	29	17	1 cup
63	16	17	100g
64	40	17	1 tbsp
65	7	17	150g
66	27	18	1 cup
67	7	18	100g
68	8	18	1개
69	35	18	2tbsp
70	7	19	100g
71	2	20	100g
72	31	20	1tbsp
73	36	20	1/4cup
74	41	20	2tbsp
75	19	20	1cup
\.


--
-- Data for Name: step; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.step (id, ordering_number, recipe_id, description, s3key, s3url) FROM stdin;
1	1	1	몰드에 현미밥을 눌러 담고 강아지 그릇에 올려주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/6256782c451d6.png
2	2	1	감자 껍질을 벗기고 큼지막하게 썰어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/6256783c0f500.png
3	3	1	물 2컵이 담긴 냄비에 감자를 넣고 화씨 350도에서 끓여주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/62567844a7611.png
4	4	1	물을 끓이는 동안 그린빈 줄기를 제거하고 얇게 썰어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/6256784fec433.png
5	5	1	애호박 줄기를 제거하고 얇게 썰어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/6256785b3981a.png
6	6	1	냄비에 그린빈을 넣고 약불(화씨 175도)로 줄이고 뚜껑을 닫고 5분 동안 삶아주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/6256786d2923f.png
7	7	1	냄비에서 그린빈을 건지고 애호박을 넣고 5분 동안 끓여주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/62567879e3588.png
8	8	1	냄비에서 애호박과 감자를 건져 물기를 빼고 식혀주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/62567885695ce.png
9	9	1	냄비에 올리브 오일을 두르고 중불(화씨 350도)에서 가열 후 닭고기를 넣고 5-7분 동안 찢어주면서 볶아주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/625678901f905.png
10	10	1	감자를 삶은 뒤 포크로 잘 으깨 공 모양 몰드에 눌러 담아 강아지 그릇에 올려주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/625678a3b8e7c.png
11	11	1	애호박 조각을 꽃 모양처럼 돌돌 말아주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/625678ad927a4.png
12	12	1	남은 조각을 강아지 그릇의 밑바닥에 놓고 그린빈을 올려주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/625678b8dc57c.png
13	13	1	그릇의 가운데에 닭고기를 넣고 애호박 고명을 올려주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/625678c8c894b.png
14	14	1	요거트를 부어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/625678d396c35.png
15	15	1	급여해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220413/625678dca6f1f.png
16	1	2	두부를 물에 1시간 30분 담근 후 20분 정도 끓는 물에 넣어 간수를 빼주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220531/62957dc11f95e.png
17	3	2	키친타올로 물기를 제거해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220531/62957ddd8f772.png
18	4	2	한입 크기로 잘라주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220531/62957de616863.png
19	5	2	전자레인지에 넣고 50초 돌리고 30초 식히고 8번 반복해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220531/62957df05e3af.png
20	6	2	급여해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220531/62957df74b731.png
21	1	3	바나나를 으깬 후 바나나 자체 수분으로 오트밀이 불을 때까지 5분 정도 기다려주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220608/629ff87d0b01d.png
22	2	3	혼합물을 숟가락으로 떠서 납작하게 쿠키 모양으로 만들어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220608/629ff892c0f33.png
23	3	3	에어프라이어에 180도에서 10분 정도 구워주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220608/629ff8a63d04f.png
24	4	3	급여해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220608/629ff8b390961.png
25	1	4	단호박을 깨끗하게 씻고 씨를 다 파내 준 다음 썰어줍니다.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220527/62903ee6133cd.jpeg
26	2	4	전자레인지에 7-8분 돌려 삶아주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220527/62903efcd0954.jpeg
27	3	4	호박 껍질을 숟가락을 사용해 분리해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220527/62904dbe46dc3.jpeg
28	4	4	호박을 으깬 후 계란 노른자, 쌀가루를 넣고 반죽을 만들어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220527/62904e7a7aeda.jpeg
29	5	4	식감을 위해 당근을 썰어 넣어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220527/62904e8766a41.jpeg
30	6	4	반죽을 30분 정도 냉장고에 숙성해 주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220527/62904eca69dae.jpeg
31	7	4	반죽을 밀대로 펴주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220527/62904eda8dec4.jpeg
32	8	4	쿠키틀로 모양을 내 반죽을 잘라주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220527/62904ef37ff47.png
33	9	4	종이 호일을 깔고 프라이팬에 약불로 처음 8-10분, 뒤집어서 4-5분 구워주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220527/62904efc33f04.jpeg
34	10	4	급여해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220527/62904efc33f04.jpeg
35	1	5	달걀 흰자와 노른자를 분리해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220411/62538c8a11eb4.png
36	2	5	그릇에 달걀 노른자를 붓고 마요네즈를 넣어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220411/62538c8e42378.png
37	3	5	거품기로 잘 풀어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220411/62538c991fb32.png
38	4	5	염소 우유와 플레인 요거트를 조금씩 넣으면서 풀어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220411/62538ca44aee4.png
39	5	5	그릇이나 접시에 부어 급여해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220411/62538cac140ab.png
40	1	6	그린빈을 잘게 썰어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220407/624e68885a1f8.jpeg
41	2	6	당근 줄기를 제거하고 껍질을 벗겨 얇게 썰어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220407/624e689386c71.jpeg
42	3	6	케서롤 그릇의 밑바닥에 당근 조각을 올려주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220407/624e689c60a7b.jpeg
43	4	6	그린빈과 소고기를 올리고 살짝 눌러주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220407/624e68b66ff30.jpeg
44	5	6	화씨 350도에서 25분 동안 구워주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220407/624e68c264c83.jpeg
45	6	6	굽는 동안 배를 2/3정도 반으로 잘라주고 숟가락으로 배 속을 파내서 그릇처럼 만들어 줍니다. ※ 배의 가운데 부분과 씨앗은 꼭 제거해주세요 ※	\N	https://recipe.bom.co.kr/uploads/posts/images/20220407/624e68d2efc3a.jpeg
46	7	6	배가 담긴 그릇에 소고기 채소 혼합물을 부어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220407/624e68dcbb8b8.jpeg
47	8	6	완전히 식힌 후 급여해주세요. ※ 배의 껍질은 잘 씻어주시고 어린 강아지라면 껍질까지 벗긴 후 과육 위주로 급여해주세요. ※	\N	https://recipe.bom.co.kr/uploads/posts/images/20220407/624e68e72710a.jpeg
48	1	7	큰 그릇에 통밀가루를 담아주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220406/624d02df29a98.jpeg
49	2	7	연어를 넣고 잘 으깨주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220406/624d02ed8875a.png
50	3	7	그릇에 요거트를 부어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220406/624d02f5c7a73.jpeg
51	4	7	재료들이 잘 혼합되도록 골고루 섞어 혼합물이 반죽이 되도록 골고루 손으로 치대주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220406/624d030c36da3.gif
52	5	7	유산지 위에 반죽을 올리고 밀대로 반죽을 1cm 두께로 밀어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220406/624d031ac3140.png
53	6	7	쿠키틀로 반죽을 잘라주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220406/624d0327ce016.gif
54	7	7	베이킹 시트 위에 올리고 화씨 325도에서 30-40분 동안 구워주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220406/624d03305df36.gif
55	8	7	음식을 완전히 식힌 후 급여해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220406/624d033c204b0.png
56	1	8	큰 그릇에 닭고기를 넣어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220404/624a59b369bb0.jpeg
57	2	8	오트밀 가루와 우유 가루를 넣어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220404/624a59ccd7619.png
58	3	8	재료들이 잘 혼합되도록 골고루 섞어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220404/624a59d62eab0.gif
59	4	8	혼합물을 작은 공 모양으로 만들어서 오븐용 그릇에 넣어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220404/624a59e71bc13.jpeg
60	5	8	화씨 350도에서 15-25분 동안 구워주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220404/624a59ee367c8.jpeg
61	6	8	완전히 식힌 후 급여해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220404/624a59f4cfb03.png
62	1	9	큰 그릇에 밀가루를 넣어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220401/6246cfeaaa72d.jpeg
63	2	9	베이킹 파우더를 넣고 달걀을 깨서 넣습니다.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220401/6246cff103080.jpeg
64	3	9	그릇에 꿀과 사과즙을 부어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220401/6246cff720626.jpeg
65	4	9	치즈를 넣고 골고루 섞어줍니다.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220401/6246d0049fe21.jpeg
66	5	9	혼합물이 반죽이 되도록 손으로 치대줍니다.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220401/6246d00b7a795.jpeg
67	6	9	반죽을 유산지 위에 올리고 밀대를 사용해서 반죽을 1cm 두께로 밀어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220401/6246d0129f762.jpeg
68	7	9	쿠키틀로 반죽을 잘라주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220401/6246d02050712.jpeg
69	8	9	베이킹 시트 위에 올리고 화씨 350도에서 20분 정도 구워줍니다.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220401/6246d026f0a5e.jpeg
70	9	9	완전히 식힌 후 급여해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220401/6246d02dc9c3c.jpeg
71	1	10	큰 그릇에 통밀가루를 넣어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/6245107bbd417.png
72	2	10	오트밀 가루와 코코넛 가루를 넣고 골고루 섞어줍니다.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/624510cc22543.png
73	3	10	식물성 오일과 꿀, 달걀을 넣고 골고루 섞어 반죽을 만들어 주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/624510d44e1e4.png
74	4	10	평평한 표면 위에 유산지를 깔고 반죽을 올려주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/624510e14d651.png
75	5	10	밀대를 사용해서 반죽을 1cm 두께로 밀어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/624510e8eddf7.png
76	6	10	쿠키틀을 이용해 반죽을 잘라주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/624511024991c.png
77	7	10	베이킹 시트 위에 올려주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/624511095045f.png
78	8	10	화씨 370도에서 12-15분 정도 구워주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/62451111552cf.png
79	9	10	음식을 완전히 식힌 후 급여해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/62451119c31c6.png
80	1	11	닭고기를 먹기 좋은 크기로 얇게 썰어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/624513f8009f1.gif
81	2	11	건조기를 준비해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/62451404820ef.png
82	3	11	닭고기가 서로 닿지 않게 건조기 위에 나란히 올려주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/624514115706b.png
83	4	11	뚜껑을 닫고 화씨 70도에서 8-10시간 동안 건조해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/6245141e0401a.png
84	5	11	완전히 식힌 후 급여해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220331/6245142734951.png
85	1	12	사과 줄기를 제거하고 껍질을 벗길 후 깍둑썰기 해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220330/6243d7e0452f7.png
86	2	12	그릇에 썰은 사과를 담고 통밀가루를 넣어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220330/6243d7eeaca25.png
87	3	12	가운데에 동그랗게 홈을 만들어 땅콩버터, 달걀, 꿀과 식물성 오일을 넣어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220330/6243d7f40f03e.png
88	4	12	다 넣었다면 잘 섞어 반죽으로 만들어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220330/6243d80643148.png
89	5	12	평평한 표면 위에 유산지를 깔고 반죽을 올려 밀대를 사용해 반죽을 1cm 두께로 밀어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220330/6243d80fe5886.png
90	6	12	쿠키틀로 반죽을 잘라주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220330/6243d824429f0.png
91	7	12	자른 반죽을 베이킹 시트 위에 올려주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220330/6243d82f531ba.png
92	8	12	화씨 350도에서 12-15분 동안 구워주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220330/6243d8374d50e.png
93	9	12	음식을 완전히 식혀 급여해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220330/6243d84186db0.png
94	1	13	오븐용 그릇에 다진 돼지고기를 넣고 평평하게 눌러줍니다.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220328/624122dd16716.jpeg
95	2	13	사과 껍질과 씨를 제거하고 얇게 썰어 10분 동안 물이 가득 담긴 그릇에 담그세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220328/624122ef749c3.jpeg
96	3	13	화씨 320도에서 25분 동안 구워주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220328/624122fa5b4c8.jpeg
97	4	13	사과 조각을 꽃 모양처럼 돌돌 말아주고 이쑤시개로 찔러 고정해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220328/6241230ff17b8.jpeg
98	5	13	구워진 돼지고기를 그릇에 옮겨 담고 사과 고명을 위에 올려주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220328/62412321ae68e.jpeg
99	6	13	계피 가루를 소량 뿌려주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220328/624123284a1da.jpeg
100	7	13	사과에 꽂아진 이쑤시개를 빼고 급여해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220328/62412335e7804.jpeg
101	1	14	고구마 끝 부분을 자르고 껍질을 벗겨 얇은 조각으로 썰어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220324/623c06e9e3066.gif
102	2	14	유산지가 깔린 베이킹 시트 위에 고구마 조각을 펼쳐주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220324/623c06ee16b2a.gif
103	3	14	고구마 조각 위에 오일을 발라주세요. 고구마를 뒤집어서 다른 한쪽 면에도 오일을 발라주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220324/623c0706d7c19.png
104	4	14	화씨 250도에서 1시간 30분 정도 구워주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220324/623c070c51317.png
105	5	14	완전히 식힌 후 급여해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220324/623c0715da7fd.png
106	1	15	레몬 껍질을 갈아넣은 후 모든 재료를 냄비에 넣고 10분간 조리한다.	\N	https://recipe.bom.co.kr/uploads/posts/images/20190621/5d0c99f6c1533.jpeg
107	2	15	음식을 꺼내 접시에 담은 후 완전히 식으면 제공한다	\N	https://recipe.bom.co.kr/uploads/posts/images/20190621/5d0c9a0ddeb16.jpeg
108	1	16	사과와 피넛버터 꿀을 준비해주세요	\N	https://recipe.bom.co.kr/uploads/posts/images/20190121/5c454c7e956db.jpeg
109	2	16	사과를 잘게 썰어 믹서기에 갈아 주세요	\N	https://recipe.bom.co.kr/uploads/posts/images/20190121/5c454c9338a7b.jpeg
110	3	16	믹서기에 간 사과에 꿀과 피넛버터 요거트를 넣고 섞어주세요	\N	https://recipe.bom.co.kr/uploads/posts/images/20190121/5c454ca12716f.jpeg
111	4	16	아이스트레이에 넣고 4시간동안 얼리면 이제 끝>_<	\N	https://recipe.bom.co.kr/uploads/posts/images/20190121/5c454cce01f7e.jpeg
112	1	17	고구마를 반으로 자르고 껍질을 벗겨주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/625910649aec3.gif
113	2	17	닭가슴살을 먹기 좋은 크기로 잘라주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/6259106aa7df9.gif
114	3	17	물 2컵이 담긴 냄비에 고구마와 달걀을 넣어 중불(화씨 350도)에서 10분 동안 삶아주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/62591070d7bbe.png
115	4	17	냄비에서 달걀을 건져주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/6259107c7fc74.png
116	5	17	달걀 껍질을 벗기고 몰드에 5분 동안 넣어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/62591083568bc.gif
117	6	17	냄비에서 고구마를 건져 식혀주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/6259108b39175.png
118	7	17	프라이팬에 올리브 오일을 두르고 센불(화씨 400도)에서 가열합니다.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/6259109959a21.png
119	8	17	닭가슴살을 넣고 5-7분 동안 재빨리 볶아주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/625910a09cf58.png
120	9	17	다 익은 닭가슴살을 접시에 담고 몰드에 있는 달걀을 꺼내 담아주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/625910ad8c9f6.jpeg
121	10	17	프라이팬에 으깬 오트밀을 넣고 물 1/2컵을 부어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/625910b7634b0.png
122	11	17	3-5분 동안 휘저으며 볶아주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/625910c5a5670.png
123	12	17	켈프를 넣고 1분 동안 볶아주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/625910cd510ab.png
124	13	17	몰드에 으깬 오트밀을 담아주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/625910d57a01b.gif
125	14	17	큰 그릇에 고구마를 넣고 으깬 후 몰드에 넣어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/625910f8d0138.gif
126	15	17	강아지 그릇에 모든 재료들을 넣어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/625910ef3cae0.gif
127	16	17	급여해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220415/62591109991c7.png
128	1	18	물 2-3컵이 채워진 냄비에 닭고기를 넣고 10-15분 정도 삶아주세요	\N	https://recipe.bom.co.kr/uploads/posts/images/20190404/5ca5a0eabce9c.png
129	2	18	닭고기를 꺼내주세요	\N	https://recipe.bom.co.kr/uploads/posts/images/20190404/5ca5a0f498e2f.png
130	3	18	삶은 물 1/2컵을 따로 보관한 뒤 식혀주세요	\N	https://recipe.bom.co.kr/uploads/posts/images/20190404/5ca5a0fa464ae.png
131	4	18	닭고기를 작은 조각으로 자르고 뼈를 제거해주세요	\N	https://recipe.bom.co.kr/uploads/posts/images/20190404/5ca5a100898f7.png
132	5	18	커다란 그릇에 밀가루, 닭고기, 식물성 오일, 달걀을 넣고 섞어주세요	\N	https://recipe.bom.co.kr/uploads/posts/images/20190404/5ca5a1d3b0d36.jpeg
133	6	18	준비해 둔 육수를 넣고 잘 섞어주세요	\N	https://recipe.bom.co.kr/uploads/posts/images/20190404/5ca5a1930c16f.png
134	7	18	깨끗한 유산지 위에 반죽을 올려놓으세요	\N	https://recipe.bom.co.kr/uploads/posts/images/20190404/5ca5a197dc1a9.png
135	8	18	반죽에 밀가루를 뿌려주고 1cm 두께로 밀어주세요	\N	https://recipe.bom.co.kr/uploads/posts/images/20190404/5ca5a1a138bb2.png
136	9	18	네모꼴로 잘라주세요	\N	https://recipe.bom.co.kr/uploads/posts/images/20190404/5ca5a1ac3464b.png
137	10	18	화씨 325도에서 50-55분 정도 구워주세요	\N	https://recipe.bom.co.kr/uploads/posts/images/20190404/5ca5a1b177931.png
138	11	18	네모꼴로 잘라준 다음 완전히 식혀주세요	\N	https://recipe.bom.co.kr/uploads/posts/images/20190404/5ca5a1b7488ec.png
139	1	19	첫번째 닭을 씻어서 가볍게 두드린다음	\N	https://recipe.bom.co.kr/uploads/posts/images/20190118/5c415a3ba0fec.jpeg
140	2	19	얇게 자르고 조각 낸 닭 가슴살위에 파슬리를 뿌리세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20190118/5c415a49979b1.jpeg
141	3	19	닭가슴살 조각들을 건조기에 고르게 놓은 다음 9시간을 건조시키세요	\N	https://recipe.bom.co.kr/uploads/posts/images/20190118/5c415a730f0cd.jpeg
142	1	20	바나나 껍질을 벗기고 믹서기에 넣어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220329/6242750bc153e.gif
143	2	20	꿀과 땅콩버터, 요거트를 넣어주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220329/624275140be0a.png
144	3	20	뚜껑을 닫고 갈아주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220329/62427520415d8.png
145	4	20	아이스크림 틀에 말린 크렌베리를 넣은 후 갈은 혼합물을 붓고 뚜껑을 닫아주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220329/62427520415d8.png
146	5	20	냉동실에 틀을 넣고 4시간 정도 얼려줍니다.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220329/6242753e88adc.png
147	6	20	급여해주세요.	\N	https://recipe.bom.co.kr/uploads/posts/images/20220329/624275471b871.png
\.


--
-- Name: bookmark_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bookmark_id_seq', 1, false);


--
-- Name: comment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comment_id_seq', 1, false);


--
-- Name: forbidden_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.forbidden_id_seq', 1, true);


--
-- Name: ingredient_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ingredient_id_seq', 41, true);


--
-- Name: meeting_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.meeting_id_seq', 2, true);


--
-- Name: member_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.member_id_seq', 4, true);


--
-- Name: pet_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pet_id_seq', 3, true);


--
-- Name: recipe_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.recipe_id_seq', 20, true);


--
-- Name: recipe_ingredient_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.recipe_ingredient_id_seq', 75, true);


--
-- Name: step_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.step_id_seq', 147, true);


--
-- Name: bookmark bookmark_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bookmark
    ADD CONSTRAINT bookmark_pkey PRIMARY KEY (id);


--
-- Name: comment comment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_pkey PRIMARY KEY (id);


--
-- Name: forbidden forbidden_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forbidden
    ADD CONSTRAINT forbidden_pkey PRIMARY KEY (id);


--
-- Name: ingredient ingredient_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredient
    ADD CONSTRAINT ingredient_name_key UNIQUE (name);


--
-- Name: ingredient ingredient_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredient
    ADD CONSTRAINT ingredient_pkey PRIMARY KEY (id);


--
-- Name: ingredient_recipe_ingredients ingredient_recipe_ingredients_recipe_ingredients_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredient_recipe_ingredients
    ADD CONSTRAINT ingredient_recipe_ingredients_recipe_ingredients_id_key UNIQUE (recipe_ingredients_id);


--
-- Name: meeting meeting_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.meeting
    ADD CONSTRAINT meeting_pkey PRIMARY KEY (id);


--
-- Name: member member_nickname_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT member_nickname_key UNIQUE (nickname);


--
-- Name: member member_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT member_pkey PRIMARY KEY (id);


--
-- Name: member member_refresh_token_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT member_refresh_token_key UNIQUE (refresh_token);


--
-- Name: member member_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT member_username_key UNIQUE (username);


--
-- Name: pet pet_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pet
    ADD CONSTRAINT pet_pkey PRIMARY KEY (id);


--
-- Name: recipe_ingredient recipe_ingredient_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe_ingredient
    ADD CONSTRAINT recipe_ingredient_pkey PRIMARY KEY (id);


--
-- Name: recipe recipe_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe
    ADD CONSTRAINT recipe_pkey PRIMARY KEY (id);


--
-- Name: step step_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.step
    ADD CONSTRAINT step_pkey PRIMARY KEY (id);


--
-- Name: pet fk1c9th7o408mgfqkbjjkvkl4mw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pet
    ADD CONSTRAINT fk1c9th7o408mgfqkbjjkvkl4mw FOREIGN KEY (member_id) REFERENCES public.member(id);


--
-- Name: bookmark fk5bm7rup91j277mc7gg63akie2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bookmark
    ADD CONSTRAINT fk5bm7rup91j277mc7gg63akie2 FOREIGN KEY (member_id) REFERENCES public.member(id);


--
-- Name: forbidden fk7iya3ex2ibqpkje6fdroyejo8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forbidden
    ADD CONSTRAINT fk7iya3ex2ibqpkje6fdroyejo8 FOREIGN KEY (ingredient_id) REFERENCES public.ingredient(id);


--
-- Name: forbidden fk8f1mbhnp4g8vvc07c4yomxrjq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.forbidden
    ADD CONSTRAINT fk8f1mbhnp4g8vvc07c4yomxrjq FOREIGN KEY (pet_id) REFERENCES public.pet(id);


--
-- Name: ingredient_recipe_ingredients fk8nk2ifrtjfd05jetjmoyn2apc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredient_recipe_ingredients
    ADD CONSTRAINT fk8nk2ifrtjfd05jetjmoyn2apc FOREIGN KEY (ingredient_id) REFERENCES public.ingredient(id);


--
-- Name: recipe_ingredient fk9b3oxoskt0chwqxge0cnlkc29; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe_ingredient
    ADD CONSTRAINT fk9b3oxoskt0chwqxge0cnlkc29 FOREIGN KEY (ingredient_id) REFERENCES public.ingredient(id);


--
-- Name: bookmark fkd7wpd757bxutqdt02bove1r9h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bookmark
    ADD CONSTRAINT fkd7wpd757bxutqdt02bove1r9h FOREIGN KEY (recipe_id) REFERENCES public.recipe(id);


--
-- Name: comment fke5i1rxybcm40jcn98fj1jmvit; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fke5i1rxybcm40jcn98fj1jmvit FOREIGN KEY (recipe_id) REFERENCES public.recipe(id);


--
-- Name: recipe_ingredient fkgu1oxq7mbcgkx5dah6o8geirh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe_ingredient
    ADD CONSTRAINT fkgu1oxq7mbcgkx5dah6o8geirh FOREIGN KEY (recipe_id) REFERENCES public.recipe(id);


--
-- Name: meeting fkhn86a6pj9y1fmpm3u55fkcseg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.meeting
    ADD CONSTRAINT fkhn86a6pj9y1fmpm3u55fkcseg FOREIGN KEY (member_id) REFERENCES public.member(id);


--
-- Name: comment fkmrrrpi513ssu63i2783jyiv9m; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fkmrrrpi513ssu63i2783jyiv9m FOREIGN KEY (member_id) REFERENCES public.member(id);


--
-- Name: meeting fkpiyspr1j38frpov7gqmcwvnad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.meeting
    ADD CONSTRAINT fkpiyspr1j38frpov7gqmcwvnad FOREIGN KEY (recipe_id) REFERENCES public.recipe(id);


--
-- Name: step fkpwpbn24pd57073jm669d7dwt9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.step
    ADD CONSTRAINT fkpwpbn24pd57073jm669d7dwt9 FOREIGN KEY (recipe_id) REFERENCES public.recipe(id);


--
-- Name: recipe fksoo4pxu79yt7nethdmbiks3pe; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipe
    ADD CONSTRAINT fksoo4pxu79yt7nethdmbiks3pe FOREIGN KEY (member_id) REFERENCES public.member(id);


--
-- Name: ingredient_recipe_ingredients fkswe61430pivfliusbl0g4febq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ingredient_recipe_ingredients
    ADD CONSTRAINT fkswe61430pivfliusbl0g4febq FOREIGN KEY (recipe_ingredients_id) REFERENCES public.recipe_ingredient(id);


--
-- PostgreSQL database dump complete
--

