-- Scrivere una query SQL che restituisca la lista di tutte le nazioni
-- mostrando nome, id, nome della regione e nome del continente, ordinata
-- per nome della nazione.

select c.country_id as ID_countries, c.name, r.name as name_region, c2.name as name_continent from countries c 
join regions r on c.region_id = r.region_id
join continents c2 on c2.continent_id = r.continent_id
where c.name like '%ger%'
order by c.name;

-- bonus
select c.name from countries c where c.country_id = '156';


-- tutte le lingue parlate in quella country
select l.`language` from country_languages cl 
join languages l on l.language_id = cl.language_id
where cl.country_id = '107';


-- le statistiche più recenti per quella country
select `year`, population, gdp 
from country_stats cs 
where cs.country_id = '107'
order by `year` desc
limit 1;