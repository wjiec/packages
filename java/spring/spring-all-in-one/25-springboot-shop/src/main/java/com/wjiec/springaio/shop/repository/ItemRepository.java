package com.wjiec.springaio.shop.repository;

import com.wjiec.springaio.shop.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public interface ItemRepository extends JpaRepository<Item, Long> {
}
