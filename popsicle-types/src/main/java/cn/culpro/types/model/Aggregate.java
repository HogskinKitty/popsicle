package cn.culpro.types.model;

/**
 * 聚合根接口
 * <p>
 * 所有领域模型中的聚合根均需实现此接口，定义了聚合根必须具有的获取唯一标识的能力
 *
 * @param <ID> 聚合根ID类型
 * @author HogskinKitty
 * @date 2025/04/19
 */
public interface Aggregate<ID> {
    
    /**
     * 获取聚合根的唯一标识
     *
     * @return 聚合根ID
     */
    ID getId();
} 