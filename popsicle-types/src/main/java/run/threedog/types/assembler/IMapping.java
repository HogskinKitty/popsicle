package run.threedog.types.assembler;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MapperConfig;

import java.util.List;
import java.util.stream.Stream;

/**
 * MapStruct 对象映射接口
 *
 * @author HogskinKitty
 * @date 2024/10/11
 */
@MapperConfig
public interface IMapping<SOURCE, TARGET> {
    
    /**
     * 映射同名属性
     *
     * @param source 源
     * @return 结果
     */
    TARGET sourceToTarget(SOURCE source);
    
    /**
     * 映射同名属性，反向
     *
     * @param target 目标
     * @return 结果
     */
    @InheritInverseConfiguration(name = "sourceToTarget")
    SOURCE targetToSource(TARGET target);
    
    /**
     * 映射同名属性，集合形式
     *
     * @param sources 源
     * @return 结果
     */
    @InheritConfiguration(name = "sourceToTarget")
    List<TARGET> sourceToTarget(List<SOURCE> sources);
    
    /**
     * 反向，映射同名属性，集合形式
     *
     * @param targets 目标
     * @return 结果
     */
    @InheritConfiguration(name = "targetToSource")
    List<SOURCE> targetToSource(List<TARGET> targets);
    
    /**
     * 映射同名属性，集合流形式
     *
     * @param stream 源
     * @return 结果
     */
    List<TARGET> sourceToTarget(Stream<SOURCE> stream);
    
    /**
     * 反向，映射同名属性，集合流形式
     *
     * @param stream 源
     * @return 结果
     */
    List<SOURCE> targetToSource(Stream<TARGET> stream);
    
}
