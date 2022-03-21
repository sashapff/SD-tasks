package sashapff.database;

import org.springframework.data.repository.CrudRepository;
import sashapff.utils.Share;

import java.util.Optional;

public interface ShareDB extends CrudRepository<Share, Share.ShareId> {
    Iterable<Share> getSharesByUserId(Integer userId);

    Optional<Share> getSharesByUserIdAndCompanyId(Integer userId, Integer companyId);
}
