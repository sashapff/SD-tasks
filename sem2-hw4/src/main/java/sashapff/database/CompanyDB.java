package sashapff.database;

import org.springframework.data.repository.CrudRepository;
import sashapff.utils.Company;

public interface CompanyDB extends CrudRepository<Company, Integer> {
}