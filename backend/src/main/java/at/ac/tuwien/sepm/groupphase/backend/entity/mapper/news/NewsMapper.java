package at.ac.tuwien.sepm.groupphase.backend.entity.mapper.news;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsMapper {

  News detailedNewsDtoToNews(DetailedNewsDto newsDto);

  DetailedNewsDto newsToDetailedNewsDto(News news);

  SimpleNewsDto newsToSimpleNewsDto(News news);

  List<SimpleNewsDto> newsToSimpleNewsDto(List<News> all);
}

