-- Scrivere una query SQL che restituisca la lista di tutte le nazioni
-- mostrando nome, id, nome della regione e nome del continente, ordinata
-- per nome della nazione.

select c.name as name_countries, c.country_id as ID_countries, r.name as name_region, c2.name as name_continent from countries c 
join regions r on c.region_id = r.region_id
join continents c2 on c2.continent_id = r.continent_id
order by c.name;
