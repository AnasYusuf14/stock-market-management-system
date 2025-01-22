package com.dev.stockmarketsystem.repositories;

import com.dev.stockmarketsystem.dtos.PortfolioStockDTO;
import com.dev.stockmarketsystem.models.PortfolioStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PortfolioStockRepository extends JpaRepository<PortfolioStock, Long> {

    // Query to find PortfolioStock by portfolio ID and stock ID
    @Query("SELECT ps FROM PortfolioStock ps WHERE ps.portfolio.id = :portfolioId AND ps.stock.id = :stockId")
    Optional<PortfolioStock> findByPortfolioIdAndStockId(@Param("portfolioId") Long portfolioId, @Param("stockId") Long stockId);
    // Custom query to fetch all stocks by portfolio ID
    @Query("SELECT new com.dev.stockmarketsystem.dtos.PortfolioStockDTO(ps.stock.id, ps.stock.name, ps.quantity) " +
            "FROM PortfolioStock ps WHERE ps.portfolio.id = :portfolioId")
    List<PortfolioStockDTO> findStocksByPortfolioId(@Param("portfolioId") Long portfolioId);




}


